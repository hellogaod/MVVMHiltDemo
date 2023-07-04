package com.aregyan.github.views.vp_frg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentViewpagerBinding
import com.aregyan.github.views.base.adapter.BaseFragmentPagerAdapter
import com.aregyan.github.views.tab_bar.fragment.TabBar1Fragment
import com.aregyan.github.views.tab_bar.fragment.TabBar2Fragment
import com.aregyan.github.views.tab_bar.fragment.TabBar3Fragment
import com.aregyan.github.views.tab_bar.fragment.TabBar4Fragment
import com.aregyan.github.views.viewpager.ViewPagerViewModel
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import dagger.hilt.android.AndroidEntryPoint
import me.goldze.mvvmhabit.base.view.BaseFragment


@AndroidEntryPoint
class ViewPagerGroupFragment : BaseFragment(){

    private val viewModel: ViewPagerViewModel by viewModels()

    private var _binding: FragmentViewpagerBinding? = null
    private val binding get() = _binding!!

    private var mFragments: List<Fragment>? = null
    private var titlePager: List<String>? = null

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initAndGetViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewDataBinding? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_viewpager, container, false);
        return _binding
    }

    override fun initBaseViewModel(): ViewPagerViewModel {
        return viewModel
    }

    override fun initData() {
        mFragments = pagerFragment()
        titlePager = pagerTitleString()
        //设置Adapter
        val pagerAdapter = BaseFragmentPagerAdapter(childFragmentManager, mFragments, titlePager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabs))
    }
    protected fun pagerFragment(): List<Fragment>? {
        val list: MutableList<Fragment> = ArrayList()
        list.add(TabBar1Fragment())
        list.add(TabBar2Fragment())
        list.add(TabBar3Fragment())
        list.add(TabBar4Fragment())
        return list
    }

    protected fun pagerTitleString(): List<String>? {
        val list: MutableList<String> = ArrayList()
        list.add("page1")
        list.add("page2")
        list.add("page3")
        list.add("page4")
        return list
    }
}