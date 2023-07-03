package com.aregyan.github.views.rv_multi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentMultiRvBinding
import me.goldze.mvvmhabit.base.view.BaseFragment

class MultiRecycleViewFragment : BaseFragment(){
    private val viewModel: MultiRecycleViewModel by viewModels()

    private var _binding: FragmentMultiRvBinding? = null
    private val binding get() = _binding!!

    override fun initVariableId(): Int {
        return BR.viewModel;
    }

    override fun initAndGetViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewDataBinding? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_multi_rv, container, false);
        return _binding
    }


    override fun initBaseViewModel(): MultiRecycleViewModel {
        return viewModel
    }
}