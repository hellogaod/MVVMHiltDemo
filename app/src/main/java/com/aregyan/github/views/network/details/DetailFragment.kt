package com.aregyan.github.views.network.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.data.network.model.NetworkDemoEntity
import com.aregyan.github.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import me.goldze.mvvmhabit.base.view.BaseFragment
import me.goldze.mvvmhabit.base.viewmodel.BaseViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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

    override fun initAndGetViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewDataBinding? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return _binding
    }

    override fun initBaseViewModel(): BaseViewModel {
        return viewModel
    }
}