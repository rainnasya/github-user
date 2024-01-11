package com.aca.githubuser2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aca.githubuser2.databinding.ActivityFavoriteUsersBinding

class FavoriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUsersBinding
    private lateinit var adapter: UsersAdapter
    private lateinit var viewModel: FavoriteUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteUsersViewModel::class.java)

        adapter.setOnItemClickCallback(object  : UsersAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Users) {
                Intent(this@FavoriteUsersActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERS, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL, data.avatar_url)
                    it.putExtra(DetailActivity.EXTRA_HTML, data.html_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteUsersActivity)
            rvUsers.adapter = adapter
        }

        viewModel.getFavoriteUsers()?.observe(this, {
            if (it != null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoriteUsers>): ArrayList<Users>{
        val listUsers = ArrayList<Users>()
        for (user in users){
            val userMapped = Users(
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

}
