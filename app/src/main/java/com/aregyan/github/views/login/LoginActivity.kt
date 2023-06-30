package com.aregyan.github.views.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.aregyan.github.R
import com.aregyan.github.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import me.goldze.mvvmhabit.base.BaseActivity


@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initViewObservable()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.unbind()
        _binding = null
    }

    override fun initViewObservable() {
        registorUIChangeLiveDataCallBack(viewModel);
        //监听ViewModel中pSwitchObservable的变化, 当ViewModel中执行【uc.pSwitchObservable.set(!uc.pSwitchObservable.get());】时会回调该方法
        viewModel.uc.pSwitchEvent.observe(this, object : Observer<Boolean?> {
            override fun onChanged(@Nullable aBoolean: Boolean?) {
                //pSwitchObservable是boolean类型的观察者,所以可以直接使用它的值改变密码开关的图标
                if (viewModel.uc.pSwitchEvent.value!!) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw)
                    binding.etPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                } else {
                    //密码不可见
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw_press)
                    binding.etPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                }
            }
        })
    }
}