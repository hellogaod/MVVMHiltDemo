package me.goldze.mvvmhabit.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.example.mvvmhabit.R;

/**
 * 吐司工具类
 */
public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Context context;
    // Toast对象
    private static Toast toast;
    // 文字显示的颜色
    private static int messageColor = 0;

    private static int backgroundId = 0;

    //设置字体颜色
    private static void setMessageColor(@ColorInt int messageColor) {
        ToastUtils.messageColor = messageColor;
    }

    //设置字体背景颜色
    private static void setBackgroundId(@DrawableRes int backgroundId) {
        ToastUtils.backgroundId = backgroundId;
    }


    /**
     * 在Application中初始化ToastUtils.init(this)
     *
     * @param context
     */
    public static void init(Context context) {
        ToastUtils.context = context.getApplicationContext();
    }

    /**
     * 设置 toast 背景样式和字体颜色
     *
     * @param context
     * @param backgroundId
     * @param messageColor
     */
    public static void init(Context context, @DrawableRes int backgroundId, @ColorInt int messageColor) {
        init(context);
        setMessageColor(messageColor);
        setBackgroundId(backgroundId);
    }

    /**
     * 发送Toast,默认LENGTH_SHORT
     *
     * @param resId
     */
    public static void showToast(int resId) {

        illegalException();

        showToast(context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showToast(String message) {

        showToast(message, Toast.LENGTH_SHORT);
    }

    private static void showToast(String massage, int duration) {

        illegalException();

        if (massage == null || massage.equals("")) {
            throw new UnsupportedOperationException(
                    context.getString(R.string.toast_message_empty)
            );
        }

        cancel();


       /* SpannableString spannableString = new SpannableString(massage);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, messageColor));
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        if (toast == null) {
            toast = Toast.makeText(context, massage, duration);
        } else {
            toast.setText(massage);
            toast.setDuration(duration);
        }
        // 设置显示的背景
        View view = toast.getView();

        if (backgroundId != 0) {
            try {
                view.setBackgroundResource(backgroundId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移200个单位，
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 200);

        if (messageColor != 0) {
            try {
                //设置字体颜色
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(messageColor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        toast.show();
    }

    /**
     * 在UI界面隐藏或者销毁前取消Toast显示
     */
    public static void cancel() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    private static void illegalException() {
        if (context == null)
            throw new IllegalArgumentException("ToastUtils must be call init() in custom application");
    }
}