package com.aregyan.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aregyan.data.domain.UserDetailEntity

//表对象
@Entity
data class DatabaseUserDetails constructor(
    @PrimaryKey
    val user: String,
    val avatar: String,
    val name: String,
    val userSince: String,
    val location: String
)

//bean对象
fun DatabaseUserDetails.asDomainModel(): UserDetailEntity {
    return UserDetailEntity(
        user = user,
        avatar = avatar,
        name = name,
        userSince = userSince,
        location = location
    )
}