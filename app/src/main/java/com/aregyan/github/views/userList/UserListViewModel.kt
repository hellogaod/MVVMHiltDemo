package com.aregyan.github.views.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aregyan.data.repository.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListRepository: com.aregyan.data.repository.UserListRepository
) : ViewModel() {

    val data = userListRepository.users

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userListRepository.refreshUserList()
        }
    }

}