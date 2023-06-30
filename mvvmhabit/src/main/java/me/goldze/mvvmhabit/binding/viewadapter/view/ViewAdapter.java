package me.goldze.mvvmhabit.binding.viewadapter.view;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class ViewAdapter {

    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (clickCommand != null) {
                                clickCommand.execute();
                            }
                        }
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (clickCommand != null) {
                                clickCommand.execute();
                            }
                        }
                    });
        }
    }

    /**
     * view的焦点发生变化的事件绑定
     */
    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final BindingCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFocusChangeCommand != null) {
                    onFocusChangeCommand.execute(hasFocus);
                }
            }
        });
    }
}
