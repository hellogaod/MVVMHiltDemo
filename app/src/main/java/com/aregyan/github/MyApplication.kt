package com.aregyan.github

import androidx.viewbinding.BuildConfig
import com.aregyan.github.views.login.LoginActivity
import dagger.hilt.android.HiltAndroidApp
import me.goldze.mvvmhabit.base.application.BaseApplication
import me.goldze.mvvmhabit.crash.CaocConfig
import me.goldze.mvvmhabit.utils.Utils
import timber.log.Timber

@HiltAndroidApp
class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        //初始化全局异常崩溃
        initCrash()

        Utils.init(this)
    }

    private fun initCrash() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
            .enabled(true) //是否启动全局异常捕获
            .showErrorDetails(true) //是否显示错误详细信息
            .showRestartButton(true) //是否显示重启按钮
            .trackActivities(true) //是否跟踪Activity
            .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
            .errorDrawable(R.drawable.customactivityoncrash_error_image) //错误图标
            .restartActivity(LoginActivity::class.java) //重新启动后的activity
            //                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
            //                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
            .apply()
    }
}