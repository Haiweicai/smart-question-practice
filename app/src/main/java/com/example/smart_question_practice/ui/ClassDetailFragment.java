package com.example.smart_question_practice.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;

/**
 * ClassDetail页。
 *
 * TODO 需要接入：ClassService（班级详情）
 * 当前仅负责加载布局 fragment_class_detail.xml，页面上的交互与数据均为空。
 */
public class ClassDetailFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_class_detail;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
