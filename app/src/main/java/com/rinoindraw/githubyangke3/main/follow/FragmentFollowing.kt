package com.rinoindraw.githubyangke3.main.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rinoindraw.githubyangke3.GithubUser
import com.rinoindraw.githubyangke3.databinding.FragmentFollowingBinding
import com.rinoindraw.githubyangke3.main.detail.DetailUserActivity

class FragmentFollowing : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        followingViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
            setDataToFragment(listFollowing)
            showLoading(false)
        }

        followingViewModel.getFollowing(
            arguments?.getString(DetailUserActivity.EXTRA_FRAGMENT).toString()
        )

    }

    private fun setDataToFragment(listFollowing: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        with(binding) {
            for (user in listFollowing) {
                listUser.clear()
                listUser.addAll(listFollowing)
            }
            rvUser.layoutManager = LinearLayoutManager(context)
            val adapter = FollowingAdapter(listFollowing)
            rvUser.adapter = adapter
        }
    }

    private fun showLoading(state: Boolean){
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}