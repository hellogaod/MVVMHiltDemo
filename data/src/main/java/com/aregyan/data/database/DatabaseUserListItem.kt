package com.aregyan.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aregyan.data.domain.UserListItemEntity

@Entity
data class DatabaseUserListItem constructor(
    @PrimaryKey
    val id: Int,
    val avatar: String,
    val username: String
)

fun List<DatabaseUserListItem>.asDomainModel(): List<UserListItemEntity> {
    return map {
        UserListItemEntity(
            id = it.id,
            avatar = it.avatar,
            username = it.username
        )
    }
}