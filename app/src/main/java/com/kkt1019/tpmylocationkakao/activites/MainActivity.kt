package com.kkt1019.tpmylocationkakao.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.kkt1019.tpmylocationkakao.R
import com.kkt1019.tpmylocationkakao.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.layoutChoice.

        //toolbar를 제목줄로 설정
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.actionbar_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

}