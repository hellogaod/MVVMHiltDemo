package com.aregyan.github.views.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aregyan.github.databinding.ItemUsersListBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class UsersListAdapter @Inject constructor(val clickListener: ClickListener) :
    ListAdapter<com.aregyan.data.domain.UserListItemEntity, UsersListAdapter.ViewHolder>(UsersListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemUsersListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: com.aregyan.data.domain.UserListItemEntity, clickListener: ClickListener) {
            binding.data = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUsersListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class UsersListDiffCallback : DiffUtil.ItemCallback<com.aregyan.data.domain.UserListItemEntity>() {

    override fun areItemsTheSame(oldItem: com.aregyan.data.domain.UserListItemEntity, newItem: com.aregyan.data.domain.UserListItemEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: com.aregyan.data.domain.UserListItemEntity, newItem: com.aregyan.data.domain.UserListItemEntity): Boolean {
        return oldItem == newItem
    }

}

class ClickListener @Inject constructor() {

    var onItemClick: ((com.aregyan.data.domain.UserListItemEntity) -> Unit)? = null

    fun onClick(data: com.aregyan.data.domain.UserListItemEntity) {
        onItemClick?.invoke(data)
    }
}