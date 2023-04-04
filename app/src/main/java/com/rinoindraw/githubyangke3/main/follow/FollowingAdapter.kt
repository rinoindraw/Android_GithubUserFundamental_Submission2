package com.rinoindraw.githubyangke3.main.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rinoindraw.githubyangke3.GithubUser
import com.rinoindraw.githubyangke3.databinding.ItemUserBinding


class FollowingAdapter(private val listFollowing: List<GithubUser>) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>(){
    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val following = listFollowing[position]

        with(holder.binding) {
            com.bumptech.glide.Glide.with(root.context)
                .load(following.avatarUrl)
                .circleCrop()
                .into(imgUser)
            textUsername.text = following.login
        }
    }

    override fun getItemCount(): Int = listFollowing.size
}