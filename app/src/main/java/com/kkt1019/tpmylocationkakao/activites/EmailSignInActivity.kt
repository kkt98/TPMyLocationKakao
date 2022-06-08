package com.kkt1019.tpmylocationkakao.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.kkt1019.tpmylocationkakao.G
import com.kkt1019.tpmylocationkakao.R
import com.kkt1019.tpmylocationkakao.databinding.ActivityEmailSignInBinding
import com.kkt1019.tpmylocationkakao.model.UserAccount

class EmailSignInActivity : AppCompatActivity() {

    val binding:ActivityEmailSignInBinding by lazy { ActivityEmailSignInBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //툴바를 제목줄로 설정
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        binding.btnSignin.setOnClickListener { clickSignIn() }

    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()

        return super.onSupportNavigateUp()
    }

    private fun clickSignIn(){

        var email:String = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()

        //Firebase Firestore DB에서 이메일 로그인 확인
        val db:FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("emailUsers")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get().addOnSuccessListener {

                if (it.documents.size > 0){
                    //로그인 성공
                    //firestore DB에 랜덤한 document 명을 id로 사용
                    var id:String = it.documents[0].id
                    G.userAccount = UserAccount(id, email)

                    //로그인성공했으면
                    val intent = Intent(this, MainActivity::class.java)

                    //기존 task의 모든 액티비티를 제거하고 새로운 task로 시작
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(intent)

                }else{
                    //로그인 실패
                    AlertDialog.Builder(this).setMessage("이메일 비밀번호 다시확인해주세요😥").create().show()
                    binding.etEmail.requestFocus()
                    binding.etEmail.selectAll()
                }

            }

    }
}