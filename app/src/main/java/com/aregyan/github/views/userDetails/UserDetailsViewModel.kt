package com.aregyan.github.views.userDetails

import androidx.databinding.ObservableParcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userDetailsRepository: com.aregyan.data.repository.UserDetailsRepository
) : ViewModel() {

    val userDetails = ObservableParcelable(com.aregyan.data.domain.UserDetailEntity())

    fun getUserDetails(user: String) = userDetailsRepository.getUserDetails(user)

    fun refreshUserDetails(user: String) = viewModelScope.launch(Dispatchers.IO) {
        userDetailsRepository.refreshUserDetails(user)
    }

}