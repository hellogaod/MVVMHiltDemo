package com.aregyan.github.views.tab_bar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentTabBar2Binding
import com.aregyan.github.views.tab_bar.TabViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.aregyan.github.views.base.activity.BaseFragment

@AndroidEntryPoint
class TabBar2Fragment : BaseFragment() {
    private val viewModel: TabViewModel by viewModels()

    private val binding get() = _binding as FragmentTabBar2Binding

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
       return R.layout.fragment_tab_bar_2
    }


    override fun setViewModel(): TabViewModel {
        return viewModel
    }
}