package com.aregyan.github.views.viewpager

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.aregyan.github.BR
import com.aregyan.github.R
import dagger.hilt.android.lifecycle.HiltViewModel
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent
import me.goldze.mvvmhabit.utils.ToastUtils
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter.PageTitles
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor() : BaseViewModel() {
    var itemClickEvent = SingleLiveEvent<String>()

    //给ViewPager添加ObservableList
    var items: ObservableList<ViewPagerItemViewModel> =
        ObservableArrayList<ViewPagerItemViewModel>()

    //给ViewPager添加ItemBinding
    var itemBinding = ItemBinding.of<ViewPagerItemViewModel>(BR.viewModel, R.layout.item_viewpager)

    //给ViewPager添加PageTitle
    val pageTitles: PageTitles<ViewPagerItemViewModel> =
        PageTitles { position, item -> "条目$position" }

    //ViewPager切换监听
    var onPageSelectedCommand = BindingCommand(object : BindingConsumer<Int?> {

        override fun call(index: Int?) {
            ToastUtils.showShort("ViewPager切换：$index")
        }
    })

    init {
        //模拟3个ViewPager页面
        for (i in 1..3) {
            val itemViewModel = ViewPagerItemViewModel(this, "第" + i + "个页面")
            items.add(itemViewModel)
        }
    }
}