package com.example.timebarteryeah.views.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.timebarteryeah.R
import com.example.timebarteryeah.views.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.support_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(supportToolbar)

        bnvMain.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navHome -> {
                    supportActionBar?.title = getString(R.string.text_nav_home)

                    onReplaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navListHelp -> {
                    supportActionBar?.title = getString(R.string.text_nav_list_help)

                    onReplaceFragment(ListHelpFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navAddPost -> {
                    supportActionBar?.title = getString(R.string.text_nav_add_post)

                    onReplaceFragment(AddPostFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navLeaderBoard -> {
                    supportActionBar?.title = getString(R.string.text_nav_leader_board)

                    onReplaceFragment(LeaderBoardFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navMyProfile -> {
                    supportActionBar?.title = getString(R.string.text_nav_profile)

                    onReplaceFragment(MyProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }

            return@setOnNavigationItemSelectedListener false
        }

        if(savedInstanceState == null) {
            bnvMain.selectedItemId = R.id.navHome
        }

    }

    private fun onReplaceFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, fragment!!)
                .commitAllowingStateLoss()
    }

}