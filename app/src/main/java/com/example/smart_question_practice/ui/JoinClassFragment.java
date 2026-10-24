package com.example.smart_question_practice.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;

/**
 * JoinClass页。
 *
 * TODO 需要接入：ClassService（按班级码加入）
 * 当前仅负责加载布局 fragment_join_class.xml，页面上的交互与数据均为空。
 */
public class JoinClassFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_join_class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
