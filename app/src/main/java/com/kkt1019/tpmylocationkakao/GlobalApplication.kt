package com.kkt1019.tpmylocationkakao

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "3a38c1834f3d1471b647da7ec5cdc92d")
    }

}