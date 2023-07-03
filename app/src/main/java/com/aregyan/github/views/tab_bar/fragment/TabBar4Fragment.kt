package com.aregyan.github.views.tab_bar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentTabBar4Binding
import com.aregyan.github.views.tab_bar.TabViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.goldze.mvvmhabit.base.view.BaseFragment

@AndroidEntryPoint
class TabBar4Fragment : BaseFragment() {
    private val viewModel: TabViewModel by viewModels()

    private var _binding: FragmentTabBar4Binding? = null
    private val binding get() = _binding!!

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initAndGetViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewDataBinding? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_bar_4, container, false);
        return _binding
    }

    override fun initBaseViewModel(): TabViewModel {
        return viewModel
    }
}