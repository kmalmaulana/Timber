package com.example.timebarteryeah.views.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timebarteryeah.R
import com.example.timebarteryeah.views.adapter.TabAdapter
import com.example.timebarteryeah.views.tab.AcceptPostTab
import com.example.timebarteryeah.views.tab.MyPostTab
import kotlinx.android.synthetic.main.fragment_help_list.view.*

class ListHelpFragment : Fragment() {

    private lateinit var ui: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_help_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui = view

        val tabAdapter = TabAdapter(childFragmentManager)
        tabAdapter.setTab(MyPostTab(), "My Post")
        tabAdapter.setTab(AcceptPostTab(), "Accept Post")

        ui.vpListHelp.adapter = tabAdapter
        ui.tlListHelp.setupWithViewPager(ui.vpListHelp)

    }

}