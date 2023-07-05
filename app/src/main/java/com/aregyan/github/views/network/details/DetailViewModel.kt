package com.aregyan.github.views.network.details

import androidx.databinding.ObservableField
import com.aregyan.data.network.model.NetworkDemoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {
    var entity: ObservableField<com.aregyan.data.network.model.NetworkDemoEntity.ItemsEntity>? = ObservableField<com.aregyan.data.network.model.NetworkDemoEntity.ItemsEntity>()

    fun setDemoEntity(entity: com.aregyan.data.network.model.NetworkDemoEntity.ItemsEntity) {
        this.entity!!.set(entity)
    }

}