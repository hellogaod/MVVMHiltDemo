package com.aregyan.github.views.rv_multi

import androidx.databinding.ObservableField
import me.goldze.mvvmhabit.base.viewmodel.MultiItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.ToastUtils


class MultiRecycleLeftItemViewModel(multiRecycleViewModel: MultiRecycleViewModel,text:String) : MultiItemViewModel(multiRecycleViewModel){

    var text = ObservableField(text)

    //条目的点击事件
    var itemClick: BindingCommand<*> = BindingCommand<Any?>(BindingAction { //拿到position
        val position: Int =( viewModel as MultiRecycleViewModel).observableList.indexOf(this@MultiRecycleLeftItemViewModel)
        ToastUtils.showShort("position：$position")
    })

}