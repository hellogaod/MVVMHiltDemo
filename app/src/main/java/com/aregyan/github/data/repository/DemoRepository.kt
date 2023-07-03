package com.aregyan.github.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aregyan.github.data.network.DemoApiService
import com.aregyan.github.data.network.model.BaseResponse
import com.aregyan.github.data.network.model.NetworkDemoEntity
import me.goldze.mvvmhabit.utils.SPUtils
import javax.inject.Inject

class DemoRepository @Inject constructor(
    private val demoApiService: DemoApiService
) {
    //模拟登陆
    suspend fun login() {
        //沉睡3秒钟
        Thread.sleep(3000)
    }

    //模拟获取下一页
    suspend fun loadMore(): LiveData<NetworkDemoEntity?>? {

        Thread.sleep(3000)

        val entity = NetworkDemoEntity()
        val itemsEntities: MutableList<NetworkDemoEntity.ItemsEntity> = ArrayList()
        //模拟一部分假数据
        //模拟一部分假数据
        for (i in 0..9) {
            val item = NetworkDemoEntity.ItemsEntity()
            item.setId(-1)
            item.setName("模拟条目")
            itemsEntities.add(item)
        }
        entity.setItems(itemsEntities)

        return MutableLiveData(entity)
    }

    //调用domeGet接口
    suspend fun demoGet(): BaseResponse<NetworkDemoEntity?>? = demoApiService.demoGet()

    //存储用户名
    fun saveUserName(userName: String) {
        SPUtils.getInstance().put("UserName", userName)
    }

    //存储密码
    fun savePassword(password: String) {
        SPUtils.getInstance().put("password", password)
    }

    //获取用户名
    fun getUserName(): String? = SPUtils.getInstance().getString("UserName")

    //获取密码
    fun getPassword(): String? = SPUtils.getInstance().getString("password")
}