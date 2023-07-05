package com.aregyan.github.views.network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentNetworkBinding
import dagger.hilt.android.AndroidEntryPoint
import com.aregyan.github.views.base.activity.BaseFragment
import me.goldze.mvvmhabit.utils.MaterialDialogUtils
import me.goldze.mvvmhabit.utils.ToastUtils

@AndroidEntryPoint
class NetWorkFragment : BaseFragment() {

    private val viewModel: NetWorkViewModel by viewModels()
    private val binding get() = _binding as FragmentNetworkBinding
    override fun initVariableId(): Int {
        return BR.viewModel
    }
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
       return R.layout.fragment_network
    }
    override fun setViewModel(): NetWorkViewModel {
        return viewModel
    }

    override fun initData() {
        //请求网络数据
        viewModel.requestNetWork()
    }

    override fun initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(this) {
            //结束刷新
            binding.twinklingRefreshLayout.finishRefreshing()

        }
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(this) {
            //结束刷新
            binding.twinklingRefreshLayout.finishLoadmore()

        }
        //监听删除条目
        viewModel.deleteItemLiveData.observe(this) { netWorkItemViewModel ->
            val index = viewModel.getItemPosition(netWorkItemViewModel)
            //删除选择对话框
            MaterialDialogUtils.showBasicDialog(
                context, "提示", "是否删除【" + netWorkItemViewModel.entity.get()!!
                    .name + "】？ position：" + index
            )
                .onNegative { dialog, which -> ToastUtils.showToast("取消") }
                .onPositive { dialog, which ->
                    viewModel.deleteItem(
                        netWorkItemViewModel
                    )
                }.show()

        }
    }
}