package com.kkt1019.tpmylocationkakao.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kkt1019.tpmylocationkakao.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //둘러보기 글씨 클릭으로 로그인없이 Main화면 이동
        binding.tvGo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //회원가입 버튼 클릭
        binding.tvSignup.setOnClickListener {

            //회원가입으로 전환
            startActivity(Intent(this, SingUpActivity::class.java))

        }

        //이메일 로그인 버튼 클릭
        binding.layoutLoginBtn.setOnClickListener {  }

        //간편로그인 클릭
        binding.btnLoginKakao.setOnClickListener { clickLoginKakao() }
        binding.btnLoginGoogle.setOnClickListener { clickLoginGoogle() }
        binding.btnLoginNaver.setOnClickListener { clickLoginNaver() }
    }

    private fun clickLoginKakao(){
        //TODO::회원가입화면작업.
    }

    private fun clickLoginGoogle(){

    }

    private fun clickLoginNaver(){

    }

}