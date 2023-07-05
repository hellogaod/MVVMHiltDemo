package com.aregyan.github.views.form

import android.text.TextUtils
import android.view.View
import androidx.databinding.ObservableBoolean
import com.aregyan.data.domain.FormEntity
import com.aregyan.data.domain.SpinnerItemData
import com.aregyan.github.views.base.viewmodel.ToolbarViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent
import me.goldze.mvvmhabit.utils.ToastUtils
import javax.inject.Inject


@HiltViewModel
class FormViewModel @Inject constructor() : ToolbarViewModel() {
    var entity: com.aregyan.data.domain.FormEntity? = null

    var sexItemDatas: ArrayList<IKeyAndValue>? = null
    var entityJsonLiveData = SingleLiveEvent<String>()

    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable? = null

    class UIChangeObservable {
        //显示日期对话框
        var showDateDialogObservable: ObservableBoolean

        init {
            showDateDialogObservable = ObservableBoolean(false)
        }
    }

    init {
        uc = UIChangeObservable()
        //sexItemDatas 一般可以从本地Sqlite数据库中取出数据字典对象集合，让该对象实现IKeyAndValue接口
        //sexItemDatas 一般可以从本地Sqlite数据库中取出数据字典对象集合，让该对象实现IKeyAndValue接口
        sexItemDatas = ArrayList()
        sexItemDatas?.add(com.aregyan.data.domain.SpinnerItemData("男", "1"))
        sexItemDatas?.add(com.aregyan.data.domain.SpinnerItemData("女", "2"))
    }

    /**
     * 初始化Toolbar
     */
    fun initToolbar() {
        //初始化标题栏
        setRightTextVisible(View.VISIBLE)
        if (TextUtils.isEmpty(entity!!.id)) {
            //ID为空是新增
            setTitleText("表单提交")
        } else {
            //ID不为空是修改
            setTitleText("表单编辑")
        }
    }

    override fun rightTextOnClick() {
        ToastUtils.showToast("更多")
    }

    fun setFormEntity(entity: com.aregyan.data.domain.FormEntity?) {
        if (this.entity == null) {
            this.entity = entity
        }
    }

    //性别选择的监听
    var onSexSelectorCommand = BindingCommand(object : BindingConsumer<IKeyAndValue?> {
        override fun call(iKeyAndValue: IKeyAndValue?) {
            entity!!.sex = iKeyAndValue?.value
        }
    })

    //生日选择的监听
    var onBirClickCommand: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { //回调到view层(Fragment)中显示日期对话框
            uc!!.showDateDialogObservable.set(!uc!!.showDateDialogObservable.get())
        })

    //是否已婚Switch点状态改变回调
    var onMarryCheckedChangeCommand = BindingCommand(object : BindingConsumer<Boolean?> {
        override fun call(isChecked: Boolean?) {
            entity!!.marry = isChecked
        }
    })

    //提交按钮点击事件
    var onCmtClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        val submitJson: String = Gson().toJson(entity)
        entityJsonLiveData.setValue(submitJson)
    })

    fun setBir(year: Int, month: Int, dayOfMonth: Int) {
        //设置数据到实体中，自动刷新界面
        entity!!.bir = year.toString() + "年" + (month + 1) + "月" + dayOfMonth + "日"
        //刷新实体,驱动界面更新
        entity!!.notifyChange()
    }
}