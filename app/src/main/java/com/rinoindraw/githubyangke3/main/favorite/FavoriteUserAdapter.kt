package com.rinoindraw.githubyangke3.main.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rinoindraw.githubyangke3.main.detail.DetailUserActivity
import com.rinoindraw.githubyangke3.database.FavoriteUser
import com.rinoindraw.githubyangke3.databinding.ItemUserBinding

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {
    private val listFavorites = ArrayList<FavoriteUser>()

    fun setFavorites(favorites: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, favorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(favorites)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteUserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorites: FavoriteUser) {
            with(binding) {
                textUsername.text = favorites.login
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USER, favorites.login)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(favorites.avatarUrl)
                .circleCrop()
                .into(binding.imgUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val itemRowUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(itemRowUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val favorites = listFavorites[position]
        holder.bind(favorites)
    }

    override fun getItemCount(): Int = listFavorites.size
}