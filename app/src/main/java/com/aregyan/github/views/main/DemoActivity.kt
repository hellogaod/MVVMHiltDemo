package com.aregyan.github.views.main

import android.Manifest
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.ActivityDemoBinding
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.functions.Consumer
import me.goldze.mvvmhabit.base.view.BaseActivity
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import me.goldze.mvvmhabit.utils.ToastUtils


@AndroidEntryPoint
class DemoActivity : BaseActivity() {

    private val viewModel: DemoViewModel by viewModels()

    private var _binding: ActivityDemoBinding? = null
    private val binding get() = _binding!!

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initAndGetViewDataBinding(): ViewDataBinding? {
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_demo)
        return _binding
    }

    override fun initBaseViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initViewObservable() {
        //注册监听相机权限的请求
        viewModel.requestCameraPermissions.observe(this, object : Observer<Boolean?> {
            override fun onChanged(@Nullable aBoolean: Boolean?) {
                requestCameraPermissions()
            }
        })
    }

    /**
     * 请求相机权限
     */
    private fun requestCameraPermissions() {
        //请求打开相机权限
        val rxPermissions = RxPermissions(this@DemoActivity)
        rxPermissions.request(Manifest.permission.CAMERA)
            .subscribe(object : Consumer<Boolean?> {

                override fun accept(aBoolean: Boolean?) {
                    if (aBoolean == true) {
                        ToastUtils.showShort("相机权限已经打开，直接跳入相机")
                    } else {
                        ToastUtils.showShort("权限被拒绝")
                    }
                }
            })
    }
}