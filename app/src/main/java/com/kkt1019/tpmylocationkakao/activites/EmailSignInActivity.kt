package com.kkt1019.tpmylocationkakao.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kkt1019.tpmylocationkakao.databinding.ActivityEmailSignInBinding

class EmailSignInActivity : AppCompatActivity() {

    val binding:ActivityEmailSignInBinding by lazy { ActivityEmailSignInBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}