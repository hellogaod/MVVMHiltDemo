package com.aregyan.github.views.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.aregyan.github.BR
import com.aregyan.github.R
import com.aregyan.github.data.domain.FormEntity
import com.aregyan.github.databinding.FragmentFormBinding
import dagger.hilt.android.AndroidEntryPoint
import me.goldze.mvvmhabit.base.view.BaseFragment
import me.goldze.mvvmhabit.utils.MaterialDialogUtils
import java.util.*

@AndroidEntryPoint
class FormFragment : BaseFragment() {

    private val viewModel: FormViewModel by viewModels()

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    private var entity: FormEntity? = FormEntity()

    override fun initParam() {
        //获取列表传入的实体
        val mBundle = arguments
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity")
        }
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initAndGetViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewDataBinding? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form, container, false);
        return _binding
    }


    override fun initBaseViewModel(): FormViewModel {
        return viewModel
    }

    override fun initData() {
        //通过binding拿到toolbar控件, 设置给Activity
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.include.toolbar)
        //View层传参到ViewModel层
        viewModel.setFormEntity(entity)
        //初始化标题
        viewModel.initToolbar()
    }

    override fun initViewObservable() {
        //监听日期选择
        viewModel.uc!!.showDateDialogObservable.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val calendar: Calendar = Calendar.getInstance()
                val year: Int = calendar.get(Calendar.YEAR)
                val month: Int = calendar.get(Calendar.MONTH)
                val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    context!!,
                    { view, year, month, dayOfMonth -> viewModel.setBir(year, month, dayOfMonth) },
                    year,
                    month,
                    day
                )
                datePickerDialog.setMessage("生日选择")
                datePickerDialog.show()
            }
        })
        viewModel.entityJsonLiveData.observe(this) { submitJson ->
            MaterialDialogUtils.showBasicDialog(context, "提交的json实体数据：\r\n$submitJson").show()

        }
    }
}