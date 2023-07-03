package com.aregyan.github.views.rv_multi

import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import me.goldze.mvvmhabit.base.viewmodel.MultiItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.ToastUtils


class MultiRecycleHeadViewModel(viewModel: BaseViewModel?) :
    MultiItemViewModel(viewModel!!) {
    //条目的点击事件
    var itemClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { ToastUtils.showShort("我是头布局") })
}