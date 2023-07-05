package com.aregyan.github.views.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mvvmhabit.R;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import me.goldze.mvvmhabit.base.view.IBaseView;
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel;
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel.ParameterField;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;

/**
 * Created by goldze on 2017/6/15.
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    protected ViewDataBinding _binding;
    protected BaseViewModel _viewModel;
    private int viewModelId;
    private MaterialDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //私有的初始化Databinding和ViewModel方法
        this._binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return _binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (_viewModel != null) {
            _viewModel.removeRxBus();
            _viewModel = null;
        }
        if (_binding != null) {
            _binding.unbind();
            _binding = null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();

        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        _viewModel.registerRxBus();
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {

        this._viewModel = setViewModel();

        if (this._binding == null) {
            throw new IllegalArgumentException(getString(R.string.init_binding_error));
        }

        if (this._viewModel == null) {
            throw new IllegalArgumentException(getString(R.string.init_viewmodel_error));
        }

        viewModelId = initVariableId();

        //关联ViewModel
        this._binding.setVariable(viewModelId, _viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        this._binding.setLifecycleOwner(this);
    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        //加载对话框显示
        _viewModel.getUC().getShowDialogEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showDialog(title);
            }
        });
        //加载对话框消失
        _viewModel.getUC().getDismissDialogEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                dismissDialog();
            }
        });
        //跳入新页面
        _viewModel.getUC().getStartActivityEvent().observe(getViewLifecycleOwner(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity
        _viewModel.getUC().getStartContainerActivityEvent().observe(getViewLifecycleOwner(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        _viewModel.getUC().getFinishEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().finish();
            }
        });
        //关闭上一层
        _viewModel.getUC().getOnBackPressedEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().onBackPressed();
            }
        });
    }

    public void showDialog(String title) {
        if (dialog != null) {
            dialog = dialog.getBuilder().title(title).build();
            dialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(getActivity(), title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {

        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.putExtra("fragment", canonicalName);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivity(intent);
    }

    /**
     * =====================================================================
     **/

    //刷新布局
    public void refreshLayout() {
        if (_viewModel != null) {
            _binding.setVariable(viewModelId, _viewModel);
        }
    }

    @Override
    public void initParam() {

    }


    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract BaseViewModel setViewModel();

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initViewObservable() {

    }

    public boolean isBackPressed() {
        return false;
    }


}
