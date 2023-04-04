package com.rinoindraw.githubyangke3.main.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rinoindraw.githubyangke3.GithubUser
import com.rinoindraw.githubyangke3.databinding.ItemUserBinding


class FollowerAdapter(private val listFollower: List<GithubUser>) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>(){
    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = listFollower[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(follower.avatarUrl)
                .circleCrop()
                .into(imgUser)
            textUsername.text = follower.login
        }
    }

    override fun getItemCount(): Int = listFollower.size
}