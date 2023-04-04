package com.rinoindraw.githubyangke3.main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rinoindraw.githubyangke3.*
import com.rinoindraw.githubyangke3.database.FavoriteUser
import com.rinoindraw.githubyangke3.databinding.ActivityDetailUserBinding
import com.rinoindraw.githubyangke3.main.SectionPagerAdapter
import com.rinoindraw.githubyangke3.main.ViewModelFactory
import com.rinoindraw.githubyangke3.response.DetailResponse

@Suppress("DEPRECATION", "NAME_SHADOWING")
class DetailUserActivity : AppCompatActivity() {
    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDetailViewModel: DetailUserViewModel
    private var detailUser = DetailResponse()

    private var buttonState: Boolean = false
    private var favoriteUser: FavoriteUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userDetailViewModel = obtainViewModel(this@DetailUserActivity)

        showLoading(true)

        userDetailViewModel.listDetail.observe(this) { detailList ->
            setDataToView(detailList)
            showLoading(false)
        }

        setTabLayoutView()

        userDetailViewModel.listDetail.observe(this) { detailList ->
            detailUser = detailList
            setDataToView(detailUser)
            favoriteUser = FavoriteUser(detailUser.id, detailUser.login)
            userDetailViewModel.getAllFavorites().observe(this) { favoriteList ->
                if (favoriteList != null) {
                    for (data in favoriteList) {
                        if (detailUser.id == data.id) {
                            buttonState = true
                            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                        }
                    }
                }
            }

            binding.fabFavorite.setOnClickListener {
                if (!buttonState) {
                    buttonState = true
                    binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    insertToDatabase(detailUser)
                } else {
                    buttonState = false
                    binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    userDetailViewModel.delete(detailUser.id)
                    showToast(getString(R.string.deleted_favorite))
                }
            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun insertToDatabase(detailList: DetailResponse) {
        favoriteUser.let { favoriteUser ->
            favoriteUser?.id = detailList.id
            favoriteUser?.login = detailList.login
            favoriteUser?.htmlUrl = detailList.htmlUrl
            favoriteUser?.avatarUrl = detailList.avatarUrl
            userDetailViewModel.insert(favoriteUser as FavoriteUser)
            showToast(getString(R.string.added_favorite))
        }
    }

    private fun setTabLayoutView() {
        val userIntent = intent.extras
        if (userIntent != null) {
            val userLogin = userIntent.getString(EXTRA_USER)
            if (userLogin != null) {
                userDetailViewModel.setUserDetail(userLogin)
                val login = Bundle()
                login.putString(EXTRA_FRAGMENT, userLogin)
                val sectionPagerAdapter = SectionPagerAdapter(this, login)
                val viewPager: ViewPager2 = binding.viewPager

                viewPager.adapter = sectionPagerAdapter
                val tabs: TabLayout = binding.tabs
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            } else {
                val userIntent = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
                userDetailViewModel.setUserDetail(userIntent.login)

                val login = Bundle()
                login.putString(EXTRA_FRAGMENT, userIntent.login)

                val sectionPagerAdapter = SectionPagerAdapter(this, login)
                val viewPager: ViewPager2 = binding.viewPager

                viewPager.adapter = sectionPagerAdapter
                val tabs: TabLayout = binding.tabs
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }

    private fun setDataToView(detailList: DetailResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailList.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageProfile)
            textName.text = detailList.name ?: "No Name."
            textUsername.text = detailList.login ?: "No Username."
            textFollowers.text = resources.getString(R.string.follower, detailList.followers)
            textFollowing.text = resources.getString(R.string.following, detailList.following)
            textRepository.text = resources.getString(R.string.repository, detailList.publicRepos)
            textWebsite.text = if (detailList.blog == "") "No website/blog." else detailList.blog
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAGMENT = "extra_fragment"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

}