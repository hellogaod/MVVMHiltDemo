package com.aregyan.github.views.network

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import com.aregyan.github.R
import com.aregyan.github.data.network.model.NetworkDemoEntity
import com.aregyan.github.views.network.details.DetailFragment
import me.goldze.mvvmhabit.base.viewmodel.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.ToastUtils
import me.goldze.mvvmhabit.utils.Utils


class NetWorkItemViewModel (networkViewModel:NetWorkViewModel, itemEntity: NetworkDemoEntity.ItemsEntity): ItemViewModel(networkViewModel) {

    var entity: ObservableField<NetworkDemoEntity.ItemsEntity> = ObservableField<NetworkDemoEntity.ItemsEntity>(itemEntity)
    var drawableImg: Drawable? =  ContextCompat.getDrawable(Utils.getContext(), R.mipmap.ic_launcher)


    /**
     * 获取position的方式有很多种,indexOf是其中一种，常见的还有在Adapter中、ItemBinding.of回调里
     *
     * @return
     */
    fun getPosition(): Int {
        return (viewModel as NetWorkViewModel).getItemPosition(this)
    }

    //条目的点击事件
    var itemClick: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        //这里可以通过一个标识,做出判断，已达到跳入不同界面的逻辑
        if (entity.get()?.getId() === -1) {
            (viewModel as NetWorkViewModel).deleteItemLiveData.setValue(this@NetWorkItemViewModel)
        } else {
            //跳转到详情界面,传入条目的实体对象
            val mBundle = Bundle()
            mBundle.putParcelable("entity", entity.get())
            viewModel.startContainerActivity(DetailFragment::class.java.getCanonicalName(), mBundle)
        }
    })

    //条目的长按事件
    var itemLongClick: BindingCommand<*> =
        BindingCommand<Any?>(BindingAction { //以前是使用Messenger发送事件，在NetWorkViewModel中完成删除逻辑
//            Messenger.getDefault().send(NetWorkItemViewModel.this, NetWorkViewModel.TOKEN_NETWORKVIEWMODEL_DELTE_ITEM);
            //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
            ToastUtils.showShort(entity.get()?.getName())
        })
}