package com.aregyan.github.views.main

import android.Manifest
import android.app.ProgressDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.ActivityDemoBinding
import com.aregyan.github.views.base.activity.BaseActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.functions.Consumer
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import me.goldze.mvvmhabit.http.download.ProgressCallBack
import me.goldze.mvvmhabit.utils.ToastUtils
import okhttp3.ResponseBody


@AndroidEntryPoint
class DemoActivity : BaseActivity() {

    private val viewModel: DemoViewModel by viewModels()

    private val binding get() = _binding as ActivityDemoBinding

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_demo
    }

    override fun setViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initViewObservable() {
        //注册监听相机权限的请求
        viewModel.requestCameraPermissions.observe(this, object : Observer<Boolean?> {
            override fun onChanged(@Nullable aBoolean: Boolean?) {
                requestCameraPermissions()
            }
        })

        //注册文件下载的监听
        viewModel.loadUrlEvent.observe(
            this
        ) { url -> downFile(url!!) }
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
                        ToastUtils.showToast("相机权限已经打开，直接跳入相机")
                    } else {
                        ToastUtils.showToast("权限被拒绝")
                    }
                }
            })
    }

    private fun downFile(url: String) {
        val destFileDir = application.cacheDir.path
        val destFileName = System.currentTimeMillis().toString() + ".apk"
        val progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.setTitle("正在下载...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        viewModel.downLoadManager?.load(
            url,
            object : ProgressCallBack<ResponseBody?>(destFileDir, destFileName) {
                override fun onStart() {
                    super.onStart()
                }

                override fun onCompleted() {
                    progressDialog.dismiss()
                }

                override fun onSuccess(responseBody: ResponseBody?) {
                    ToastUtils.showToast("文件下载完成！")
                }

                override fun progress(progress: Long, total: Long) {
                    progressDialog.max = total.toInt()
                    progressDialog.progress = progress.toInt()
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    ToastUtils.showToast("文件下载失败！")
                    progressDialog.dismiss()
                }
            })
    }
}