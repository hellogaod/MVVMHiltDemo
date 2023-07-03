package com.aregyan.github.views.rv_multi

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.aregyan.github.BR
import com.aregyan.github.R
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import me.goldze.mvvmhabit.base.viewmodel.MultiItemViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding


class MultiRecycleViewModel(): BaseViewModel() {
    private val MultiRecycleType_Head = "head"
    private val MultiRecycleType_Left = "left"
    private val MultiRecycleType_Right = "right"

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<MultiItemViewModel> = ObservableArrayList()


    init {
        //模拟10个条目，数据源可以来自网络

        //模拟10个条目，数据源可以来自网络
        for (i in 0..19) {
            if (i == 0) {
                val item: MultiItemViewModel = MultiRecycleHeadViewModel(this)
                //条目类型为头布局
                item.multiItemType(MultiRecycleType_Head)
                observableList.add(item)
            } else {
                val text = "我是第" + i + "条"
                if (i % 2 == 0) {
                    val item: MultiItemViewModel = MultiRecycleLeftItemViewModel(this, text)
                    //条目类型为左布局
                    item.multiItemType(MultiRecycleType_Left)
                    observableList.add(item)
                } else {
                    val item: MultiItemViewModel = MultiRecycleRightItemViewModel(this, text)
                    //条目类型为右布局
                    item.multiItemType(MultiRecycleType_Right)
                    observableList.add(item)
                }
            }
        }
    }

    //RecyclerView多布局添加ItemBinding
    var itemBinding = ItemBinding.of<MultiItemViewModel> { itemBinding, position, item ->
        //通过item的类型, 动态设置Item加载的布局
        val itemType = item.itemType as String
        if (MultiRecycleType_Head == itemType) {
            //设置头布局
            itemBinding[BR.viewModel] = R.layout.item_multi_head
        } else if (MultiRecycleType_Left == itemType) {
            //设置左布局
            itemBinding[BR.viewModel] = R.layout.item_multi_rv_left
        } else if (MultiRecycleType_Right == itemType) {
            //设置右布局
            itemBinding[BR.viewModel] = R.layout.item_multi_rv_right
        }
    }
}