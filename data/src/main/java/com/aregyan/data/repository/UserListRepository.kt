package com.aregyan.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.aregyan.data.database.UsersDatabase
import com.aregyan.data.database.asDomainModel
import com.aregyan.data.domain.UserListItemEntity
import com.aregyan.data.network.UserListService
import com.aregyan.data.network.model.asDatabaseModel
import timber.log.Timber
import javax.inject.Inject

class UserListRepository @Inject constructor(
    private val userListService: UserListService,
    private val database: UsersDatabase
) {

    val users: LiveData<List<UserListItemEntity>> =
        Transformations.map(database.usersDao.getDatabaseUsers()) {
            it.asDomainModel()
        }

    suspend fun refreshUserList() {
        try {
            val users = userListService.getUserList()
            database.usersDao.insertAll(users.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}