package com.aregyan.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.aregyan.data.database.UsersDatabase
import com.aregyan.data.database.asDomainModel
import com.aregyan.data.domain.UserDetailEntity
import com.aregyan.data.network.UserDetailsService
import com.aregyan.data.network.model.asDatabaseModel
import timber.log.Timber
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(
    private val userDetailsService: UserDetailsService,
    private val database: UsersDatabase
) {

    fun getUserDetails(user: String): LiveData<UserDetailEntity> {
        return Transformations.map(database.usersDao.getUserDetails(user)) {
            it?.asDomainModel()
        }
    }


    suspend fun refreshUserDetails(user: String) {
        try {
            val userDetails = userDetailsService.getUserDetails(user)
            database.usersDao.insertUserDetails(userDetails.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

}