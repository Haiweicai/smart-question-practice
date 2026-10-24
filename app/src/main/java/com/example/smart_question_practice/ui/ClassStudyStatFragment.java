package com.example.smart_question_practice.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;

/**
 * ClassStudyStat页。
 *
 * TODO 需要接入：ClassService（学情统计）
 * 当前仅负责加载布局 fragment_class_study_stat.xml，页面上的交互与数据均为空。
 */
public class ClassStudyStatFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_class_study_stat;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
