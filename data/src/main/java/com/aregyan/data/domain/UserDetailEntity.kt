package com.aregyan.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailEntity(
    val user: String? = "",
    val avatar: String? = "",
    val name: String? = "",
    val userSince: String? = "",
    val location: String? = ""
) : Parcelable