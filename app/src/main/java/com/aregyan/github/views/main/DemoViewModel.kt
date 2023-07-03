package com.aregyan.github.views.main

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent
import javax.inject.Inject


/**
 * Created by goldze on 2017/7/17.
 */
@HiltViewModel
class DemoViewModel @Inject constructor() : BaseViewModel() {
    //使用Observable
    var requestCameraPermissions = SingleLiveEvent<Boolean>()

    //使用LiveData
    var loadUrlEvent = SingleLiveEvent<String>()

    //网络访问点击事件
    var netWorkClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { /*startContainerActivity(NetWorkFragment::class.java.getCanonicalName())*/ })

    //RecycleView多布局
    var rvMultiClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { /*startContainerActivity(MultiRecycleViewFragment::class.java.getCanonicalName())*/ })

    //进入TabBarActivity
    var startTabBarClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { /*startActivity(TabBarActivity::class.java)*/ })

    //ViewPager绑定
    var viewPagerBindingClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { /*startActivity(ViewPagerActivity::class.java)*/ })

    //ViewPager+Fragment
    var viewPagerGroupBindingClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { /*startContainerActivity(ViewPagerGroupFragment::class.java.getCanonicalName())*/ })

    //表单提交点击事件
    var formSbmClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { /*startContainerActivity(FormFragment::class.java.getCanonicalName())*/ })

    //表单修改点击事件
    var formModifyClick: BindingCommand<*> = BindingCommand<Any?>(BindingAction { //模拟一个修改的实体数据
       /* val entity = FormEntity()
        entity.setId("12345678")
        entity.setName("goldze")
        entity.setSex("1")
        entity.setBir("xxxx年xx月xx日")
        entity.setMarry(true)
        //传入实体数据
        val mBundle = Bundle()
        mBundle.putParcelable("entity", entity)
        startContainerActivity(FormFragment::class.java.getCanonicalName(), mBundle)*/
    })

    //权限申请
    var permissionsClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { requestCameraPermissions.call() })

    //全局异常捕获
    var exceptionClick: BindingCommand<*> = BindingCommand<Any?>(BindingAction { //伪造一个异常
        "goldze".toInt()
    })

    //文件下载
    var fileDownLoadClick: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        loadUrlEvent.value =
            "http://gdown.baidu.com/data/wisegame/dc8a46540c7960a2/baidushoujizhushou_16798087.apk"
    })
}
