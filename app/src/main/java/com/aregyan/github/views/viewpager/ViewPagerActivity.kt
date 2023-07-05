package com.aregyan.github.views.viewpager

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentViewpagerBinding
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import dagger.hilt.android.AndroidEntryPoint
import com.aregyan.github.views.base.BaseActivity
import me.goldze.mvvmhabit.utils.ToastUtils


@AndroidEntryPoint
class ViewPagerActivity : BaseActivity() {
    private val viewModel: ViewPagerViewModel by viewModels()

    private val binding get() = _binding as FragmentViewpagerBinding

    override fun initVariableId(): Int {
        return BR.viewModel;
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_viewpager
    }

    override fun setViewModel(): ViewPagerViewModel {
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
                ToastUtils.showToast("position：$text")
            }
        })
    }
}