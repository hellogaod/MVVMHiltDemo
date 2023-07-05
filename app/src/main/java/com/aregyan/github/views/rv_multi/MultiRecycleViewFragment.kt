package com.aregyan.github.views.rv_multi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.databinding.FragmentMultiRvBinding
import com.aregyan.github.views.base.BaseFragment

class MultiRecycleViewFragment : BaseFragment(){
    private val viewModel: MultiRecycleViewModel by viewModels()

    private val binding get() = _binding as FragmentMultiRvBinding

    override fun initVariableId(): Int {
        return BR.viewModel;
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
       return R.layout.fragment_multi_rv
    }


    override fun setViewModel(): MultiRecycleViewModel {
        return viewModel
    }
}