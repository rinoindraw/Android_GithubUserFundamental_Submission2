package com.rinoindraw.githubyangke3.main.follow

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rinoindraw.githubyangke3.GithubUser
import com.rinoindraw.githubyangke3.R
import com.rinoindraw.githubyangke3.databinding.FragmentFollowersBinding
import com.rinoindraw.githubyangke3.main.detail.DetailUserActivity


class FragmentFollowers : Fragment(R.layout.fragment_followers) {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        viewModel.listFollowers.observe(viewLifecycleOwner) { listFollower ->
            setDataToFragment(listFollower)
            showLoading(false)
        }

        viewModel.setListFollowers(arguments?.getString(DetailUserActivity.EXTRA_FRAGMENT).toString())

    }

    private fun setDataToFragment(listFollower: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        with(binding) {
            for (user in listFollower) {
                listUser.clear()
                listUser.addAll(listFollower)
            }
            rvUser.layoutManager = LinearLayoutManager(context)
            val adapter = FollowerAdapter(listFollower)
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