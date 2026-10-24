package com.example.smart_question_practice.core.contract;

import com.example.smart_question_practice.core.Callback;

/**
 * 应用更新检查。骨架只给出契约，版本接口由服务端自行暴露。
 */
public interface UpdateService {

    void checkUpdate(Callback<UpdateInfo> callback);

    /** 下载 APK 到本地缓存，返回绝对路径。 */
    void downloadApk(UpdateInfo info, ProgressListener listener, Callback<String> callback);

    class UpdateInfo {
        public int versionCode;
        public String versionName;
        public String downloadUrl;
        public String changelog;
        public boolean forceUpdate;
        public long sizeBytes;
    }

    interface ProgressListener {
        void onProgress(int percent);
    }
}
