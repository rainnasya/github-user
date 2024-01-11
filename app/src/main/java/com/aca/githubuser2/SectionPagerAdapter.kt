package com.aca.githubuser2

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionPagerAdapter(private val context: Context, fragmentManager: FragmentManager, data: Bundle): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val TAB_TITLE = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowersUserFragment()
            1 -> fragment = FollowingUserFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLE[position])
    }


}