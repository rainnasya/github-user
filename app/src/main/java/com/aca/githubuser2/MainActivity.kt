package com.aca.githubuser2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aca.githubuser2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : UsersViewModel
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        val pref = SettingsPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ViewThemeModelFactory(pref)).get(
            SettingsViewModel::class.java
        )

        adapter.setOnItemClickCallback(object  : UsersAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Users) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERS, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL, data.avatar_url)
                    it.putExtra(DetailActivity.EXTRA_HTML, data.html_url)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UsersViewModel::class.java)

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter

            search.setOnClickListener{
                searchUser()
            }

            edQuery.setOnKeyListener { view, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false

            }

        }
        viewModel.getSearchUser().observe(this, {
            if(it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
        themeViewModel.getTheme().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

    }

    private fun searchUser(){
        binding.apply {
            val query = edQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav_menu -> {
                Intent(this, FavoriteUsersActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.setting -> {
                Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}