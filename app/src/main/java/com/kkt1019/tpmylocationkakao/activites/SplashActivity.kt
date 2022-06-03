package com.kkt1019.tpmylocationkakao.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView()를 이용하지 않고 Theme 를 이용하여 화면 구성해보기

        //1.5초후에 자동으로 LoginActivity로 이동.
//        Handler(Looper.getMainLooper()).postDelayed(object:Runnable{
//            override fun run() {
//
//            }
//
//        } ,1500)

        //lamDa 표기로 줄여쓰기
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity( Intent(this, LoginActivity::class.java) )
            finish()
        } , 1500)
    }
}