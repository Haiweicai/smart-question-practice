package com.example.smart_question_practice.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smart_question_practice.R;

/**
 * Upload页。
 *
 * TODO 需要接入：QuestionParser（文件解析，见 parser 包）
 * 当前仅负责加载布局 fragment_upload.xml，页面上的交互与数据均为空。
 */
public class UploadFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 绑定控件并调用相应接口
    }
}
