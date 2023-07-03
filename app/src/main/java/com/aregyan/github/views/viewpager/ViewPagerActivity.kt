package com.aregyan.github.views.viewpager

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentViewpagerBinding
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import dagger.hilt.android.AndroidEntryPoint
import me.goldze.mvvmhabit.base.view.BaseActivity
import me.goldze.mvvmhabit.utils.ToastUtils


@AndroidEntryPoint
class ViewPagerActivity : BaseActivity() {
    private val viewModel: ViewPagerViewModel by viewModels()

    private var _binding: FragmentViewpagerBinding? = null
    private val binding get() = _binding!!

    override fun initVariableId(): Int {
        return BR.viewModel;
    }

    override fun initAndGetViewDataBinding(): FragmentViewpagerBinding? {
        _binding = DataBindingUtil.setContentView(this, R.layout.fragment_viewpager)
        return _binding
    }

    override fun initBaseViewModel(): ViewPagerViewModel {
        return viewModel
    }

    override fun initData() {
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabs))
        //给ViewPager设置adapter
        binding.adapter = ViewPagerBindingAdapter()
    }

    override fun initViewObservable() {
        viewModel.itemClickEvent.observe(this, object : Observer<String?> {
            override fun onChanged(text: String?) {
                ToastUtils.showShort("position：$text")
            }
        })
    }
}