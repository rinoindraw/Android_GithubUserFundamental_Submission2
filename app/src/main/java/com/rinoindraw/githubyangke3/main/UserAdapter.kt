package com.rinoindraw.githubyangke3.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rinoindraw.githubyangke3.GithubUser
import com.rinoindraw.githubyangke3.databinding.ItemUserBinding

class UserAdapter(private val listUser: List<GithubUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(imgUser)
            textUsername.text = user.login
            root.setOnClickListener { onItemClickCallback.onItemClicked(listUser[position]) }
        }
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}