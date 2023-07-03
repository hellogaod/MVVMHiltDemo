package com.aregyan.github.views.network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentNetworkBinding
import dagger.hilt.android.AndroidEntryPoint
import me.goldze.mvvmhabit.base.view.BaseFragment
import me.goldze.mvvmhabit.utils.MaterialDialogUtils
import me.goldze.mvvmhabit.utils.ToastUtils

@AndroidEntryPoint
class NetWorkFragment : BaseFragment() {

    private val viewModel: NetWorkViewModel by viewModels()

    private var _binding: FragmentNetworkBinding? = null
    private val binding get() = _binding!!

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initAndGetViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewDataBinding? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_network, container, false);
        return _binding
    }


    override fun initBaseViewModel(): NetWorkViewModel {
        return viewModel
    }

    override fun initData() {
        //请求网络数据
        viewModel.requestNetWork()
    }

    override fun initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(this) {
            //结束刷新
            binding.twinklingRefreshLayout.finishRefreshing()

        }
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(this) {
            //结束刷新
            binding.twinklingRefreshLayout.finishLoadmore()

        }
        //监听删除条目
        viewModel.deleteItemLiveData.observe(this) { netWorkItemViewModel ->
            val index = viewModel.getItemPosition(netWorkItemViewModel)
            //删除选择对话框
            MaterialDialogUtils.showBasicDialog(
                context, "提示", "是否删除【" + netWorkItemViewModel.entity.get()!!
                    .name + "】？ position：" + index
            )
                .onNegative { dialog, which -> ToastUtils.showShort("取消") }
                .onPositive { dialog, which ->
                    viewModel.deleteItem(
                        netWorkItemViewModel
                    )
                }.show()

        }
    }
}