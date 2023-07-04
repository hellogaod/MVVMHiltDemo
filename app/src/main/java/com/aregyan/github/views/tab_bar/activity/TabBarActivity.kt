package com.aregyan.github.views.tab_bar.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.ActivityTabBarBinding
import com.aregyan.github.views.tab_bar.TabViewModel
import com.aregyan.github.views.tab_bar.fragment.TabBar1Fragment
import com.aregyan.github.views.tab_bar.fragment.TabBar2Fragment
import com.aregyan.github.views.tab_bar.fragment.TabBar3Fragment
import com.aregyan.github.views.tab_bar.fragment.TabBar4Fragment
import dagger.hilt.android.AndroidEntryPoint
import me.goldze.mvvmhabit.base.view.BaseActivity
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener


@AndroidEntryPoint
class TabBarActivity : BaseActivity(){
    private var mFragments: ArrayList<Fragment>? = null

    private val viewModel: TabViewModel by viewModels()

    private val binding get() = _binding as ActivityTabBarBinding

    override fun initVariableId(): Int {
        return BR.viewModel;
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_tab_bar
    }

    override fun setViewModel(): TabViewModel {
        return viewModel
    }

    override fun initData() {
        //初始化Fragment
        initFragment()
        //初始化底部Button
        initBottomTab()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        mFragments!!.add(TabBar1Fragment())
        mFragments!!.add(TabBar2Fragment())
        mFragments!!.add(TabBar3Fragment())
        mFragments!!.add(TabBar4Fragment())
        //默认选中第一个
        commitAllowingStateLoss(0)
    }

    private fun initBottomTab() {
        val navigationController: NavigationController = binding.pagerBottomTab.material()
            .addItem(R.mipmap.yingyong, "应用")
            .addItem(R.mipmap.huanzhe, "工作")
            .addItem(R.mipmap.xiaoxi_select, "消息")
            .addItem(R.mipmap.wode_select, "我的")
            .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
            .build()
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frameLayout, mFragments.get(index));
//                transaction.commitAllowingStateLoss();
                commitAllowingStateLoss(index)
            }

            override fun onRepeat(index: Int) {}
        })
    }

    private fun commitAllowingStateLoss(position: Int) {
        hideAllFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var currentFragment = supportFragmentManager.findFragmentByTag(position.toString() + "")
        if (currentFragment != null) {
            transaction.show(currentFragment)
        } else {
            currentFragment = mFragments!![position]
            transaction.add(com.aregyan.github.R.id.frameLayout, currentFragment, position.toString() + "")
        }
        transaction.commitAllowingStateLoss()
    }

    //隐藏所有Fragment
    private fun hideAllFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        for (i in mFragments!!.indices) {
            val currentFragment = supportFragmentManager.findFragmentByTag(i.toString() + "")
            if (currentFragment != null) {
                transaction.hide(currentFragment)
            }
        }
        transaction.commitAllowingStateLoss()
    }
}