package com.aregyan.github.views.network

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.viewModelScope
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.data.repository.DemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent
import me.goldze.mvvmhabit.utils.ToastUtils
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject


@HiltViewModel
class NetWorkViewModel @Inject constructor(private val demoRepository: DemoRepository) :
    BaseViewModel() {
    var deleteItemLiveData = SingleLiveEvent<NetWorkItemViewModel>()

    //封装一个界面发生改变的观察者
    var uc = UIChangeObservable()

    class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing: SingleLiveEvent<*> = SingleLiveEvent<Any>()

        //上拉加载完成
        var finishLoadmore: SingleLiveEvent<*> = SingleLiveEvent<Any>()
    }

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<NetWorkItemViewModel> = ObservableArrayList()

    //给RecyclerView添加ItemBinding
    var itemBinding: ItemBinding<NetWorkItemViewModel> =
        ItemBinding.of(BR.viewModel, R.layout.item_network)

    //下拉刷新
    var onRefreshCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        ToastUtils.showShort("下拉刷新")
        requestNetWork()
    })

    var onLoadMoreCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        if (observableList.size > 50) {
            ToastUtils.showLong("兄dei，你太无聊啦~崩是不可能的~");
            uc.finishLoadmore.call();
            return@BindingAction;
        }
        viewModelScope.launch {
            val todo = withContext(Dispatchers.IO) {
                demoRepository.loadMore()
            }
            todo.let { entity ->
                for (itemsEntity in entity?.value?.items!!) {
                    val itemViewModel = NetWorkItemViewModel(this@NetWorkViewModel, itemsEntity)
                    //双向绑定动态添加Item
                    observableList.add(itemViewModel)
                }
                //刷新完成收回
                uc.finishLoadmore.call()
            }
        }

    })


    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
     fun requestNetWork() {
        viewModelScope.launch {
            showDialog()
            val todo = withContext(Dispatchers.IO) {
                demoRepository.demoGet()
            }
            todo.let { response ->
                dismissDialog()
                //请求刷新完成收回
                uc.finishRefreshing.call()
                //清除列表
                observableList.clear()
                //请求成功
                if (response?.value?.getCode() === 1) {
                    for (entity in response?.value?.getResult()!!.getItems()) {
                        val itemViewModel = NetWorkItemViewModel(this@NetWorkViewModel, entity)
                        //双向绑定动态添加Item
                        observableList.add(itemViewModel)
                    }
                } else {
                    //code错误时也可以定义Observable回调到View层去处理
                    ToastUtils.showShort("数据错误")
                }
            }
        }
    }

    /**
     * 删除条目
     *
     * @param netWorkItemViewModel
     */
    fun deleteItem(netWorkItemViewModel: NetWorkItemViewModel?) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList.remove(netWorkItemViewModel)
    }

    /**
     * 获取条目下标
     *
     * @param netWorkItemViewModel
     * @return
     */
    fun getItemPosition(netWorkItemViewModel: NetWorkItemViewModel?): Int {
        return observableList.indexOf(netWorkItemViewModel)
    }
}