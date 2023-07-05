package com.aregyan.github.views.network.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.data.network.model.NetworkDemoEntity
import com.aregyan.github.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import com.aregyan.github.views.base.activity.BaseFragment
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by viewModels()
    private val binding get() = _binding as FragmentDetailBinding
    private var entity: NetworkDemoEntity.ItemsEntity? = null

    override fun initParam() {
        //获取列表传入的实体
        val mBundle: Bundle? = arguments
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity")
        }
    }
    override fun initData() {
        viewModel.setDemoEntity(entity!!)
    }
    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_detail
    }

    override fun setViewModel(): BaseViewModel {
        return viewModel
    }
}