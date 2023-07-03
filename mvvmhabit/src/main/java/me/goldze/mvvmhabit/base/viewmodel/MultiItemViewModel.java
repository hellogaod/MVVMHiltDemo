package me.goldze.mvvmhabit.base.viewmodel;


import androidx.annotation.NonNull;

/**
 * Create Author：goldze
 * Create Date：2019/01/25
 * Description：RecycleView多布局ItemViewModel是封装
 */

public class MultiItemViewModel extends ItemViewModel {
    protected Object multiType;

    public Object getItemType() {
        return multiType;
    }

    public void multiItemType(@NonNull Object multiType) {
        this.multiType = multiType;
    }

    public MultiItemViewModel(@NonNull BaseViewModel viewModel) {
        super(viewModel);
    }
}
