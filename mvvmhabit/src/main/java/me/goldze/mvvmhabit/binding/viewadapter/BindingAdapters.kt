package com.aregyan.github.util

import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import me.goldze.mvvmhabit.binding.command.BindingCommand
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

//图片加载工具
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).into(view)
    }
}

//TextView时间转换工具：注意写法和loadImage方法不一致
@BindingAdapter("dateFormatter")
fun TextView.dateFormatter(string: String?) {
    if (string?.isNotEmpty() == true) {
        val date = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(string)
        date?.let {
            val format = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
            this.text = format.format(it)
        }

    }
}

/**
 * view的焦点发生变化的事件绑定
 */
@BindingAdapter("onFocusChangeCommand")
fun onFocusChangeCommand(view: View, onFocusChangeCommand: BindingCommand<Boolean?>?) {
    view.onFocusChangeListener =
        OnFocusChangeListener { v, hasFocus -> onFocusChangeCommand?.execute(hasFocus) }
}


/**
 * requireAll 是意思是是否需要绑定全部参数, false为否
 * View的onClick事件绑定
 * onClickCommand 绑定的命令,
 * isThrottleFirst 是否开启防止过快点击
 */
@BindingAdapter(value = ["onClickCommand", "isThrottleFirst"], requireAll = false)
fun onClickCommand(view: View?, clickCommand: BindingCommand<*>?, isThrottleFirst: Boolean) {
    //防重复点击间隔(秒)
    val CLICK_INTERVAL = 1

    if (isThrottleFirst) {
        RxView.clicks(view!!)
            .subscribe { clickCommand?.execute() }
    } else {
        RxView.clicks(view!!)
            .throttleFirst(CLICK_INTERVAL.toLong(), TimeUnit.SECONDS) //1秒钟内只允许点击1次
            .subscribe { clickCommand?.execute() }
    }
}
