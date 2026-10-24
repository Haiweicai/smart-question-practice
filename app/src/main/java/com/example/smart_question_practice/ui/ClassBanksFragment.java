package com.example.smart_question_practice.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;

/**
 * ClassBanks页。
 *
 * TODO 需要接入：ClassService（班级共享题库）
 * 当前仅负责加载布局 fragment_class_banks.xml，页面上的交互与数据均为空。
 */
public class ClassBanksFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_class_banks;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
