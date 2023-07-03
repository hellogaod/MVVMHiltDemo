package com.aregyan.github.views.network.details

import androidx.databinding.ObservableField
import com.aregyan.github.data.network.model.NetworkDemoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {
    var entity: ObservableField<NetworkDemoEntity.ItemsEntity>? = ObservableField<NetworkDemoEntity.ItemsEntity>()

    fun setDemoEntity(entity: NetworkDemoEntity.ItemsEntity) {
        this.entity!!.set(entity)
    }

}