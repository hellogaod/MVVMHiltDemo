package me.goldze.mvvmhabit.base.viewmodel;


import androidx.annotation.NonNull;

/**
 * ItemViewModel
 * Created by goldze on 2018/10/3.
 */

public class ItemViewModel {
    protected BaseViewModel viewModel;

    public ItemViewModel(@NonNull BaseViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
