package com.aca.githubuser2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.aca.githubuser2.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel: DetailUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERS)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val htmlUrl = intent.getStringExtra(EXTRA_HTML)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERS, username)

        viewModel = ViewModelProvider(this).get(DetailUsersViewModel::class.java)

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvLocation.text = it.location
                    tvCompany.text = it.company
                    tvFollowers.text = "${it.followers}"
                    tvFollowing.text = "${it.following}"
                    tvRepository.text = "${it.public_repos}"

                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivPicture)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUsers(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.toggleFav.isChecked = true
                        _isChecked = true
                    }else{
                        binding.toggleFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFav.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                if (username != null) {
                    if (avatarUrl != null) {
                        if (htmlUrl != null) {
                            viewModel.addFavorite(username,id,avatarUrl,htmlUrl)
                        }
                    }
                }
            }else{
                viewModel.removeFavorite(id)
            }
            binding.toggleFav.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

    }

    companion object{
        const val EXTRA_USERS = "extra_users"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
        const val EXTRA_HTML = "extra_html"
    }



}