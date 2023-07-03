package com.aregyan.github.views.viewpager

import me.goldze.mvvmhabit.base.viewmodel.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand


class ViewPagerItemViewModel(viewModel: ViewPagerViewModel, var text: String) :
    ItemViewModel(viewModel) {
    var onItemClick: BindingCommand<*> = BindingCommand<Any?>(BindingAction { //点击之后将逻辑转到activity中处理
        viewModel.itemClickEvent.setValue(text)
    })
}
