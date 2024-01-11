package com.aca.githubuser2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aca.githubuser2.databinding.FragmentFollowBinding

class FollowersUserFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersUserViewModel
    private lateinit var adapter: UsersAdapter
    private lateinit var username: String
    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        val arguments = arguments
        username = arguments?.getString(DetailActivity.EXTRA_USERS).toString()
        _binding = FragmentFollowBinding.bind(v)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersUserViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}