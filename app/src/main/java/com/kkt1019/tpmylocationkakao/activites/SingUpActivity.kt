package com.kkt1019.tpmylocationkakao.activites

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.kkt1019.tpmylocationkakao.R
import com.kkt1019.tpmylocationkakao.databinding.ActivitySingUpBinding

class SingUpActivity : AppCompatActivity() {

    val binding: ActivitySingUpBinding by lazy { ActivitySingUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //툴바를 액션바로 대체하기
        setSupportActionBar(binding.toolbar)
        //액션바에 제목글씨가 표시됨
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        binding.btnSignup.setOnClickListener { clickSignup() }
    }

    //업버튼 클릭시 반응 콜백
    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()

        return super.onSupportNavigateUp()
    }

    private fun clickSignup() {
        //Firebase FireStore DB에 사용자 정보 저장하기

        var email: String = binding.etEmail.text.toString()
        var password: String = binding.etPassword.text.toString()
        var passwordConfirm: String = binding.etPasswordConfirm.text.toString()

        //유효성 검사 - 패스워드와 패스워드확인 이 맞는지 검사
        //코틀린에서는 무자열 비교시에 equals대신 ==을 사용권장함
        if (password != passwordConfirm) {
            AlertDialog.Builder(this).setMessage("비밀번호가 같지 않습니다. 다시 입력해주세요😊").create().show()
            binding.etPasswordConfirm.selectAll()
            return
        }

        //Firebase FireStore DB 관리객체 얻어오기기
        val db = FirebaseFirestore.getInstance()

        //혹시 이미 가입되어 있는 이메일을 사용한다면... 새로 가입을 불허
        db.collection("emailUsers").whereEqualTo("email", email)
            .get().addOnSuccessListener {
                //같은 값을 가진 Document가 여러개 일 수도 있기에
                if (it.documents.size > 0){ //같은 이메일이 이미 있다는 것

                    AlertDialog.Builder(this).setMessage("중복된 이메일입니다.😖").show()
                    binding.etEmail.requestFocus() //포커스가 없으면 selectAll 이 동작하지 않음
                    binding.etEmail.selectAll()

                }else{ //같은 이메일이 없다는 것

                    //저장할 값(이메일, 비밀번호)를 저장하기 위해 HashMap 으로 만들기
                    val user:MutableMap<String, String> = mutableMapOf()
                    user.put("email", email)
                    user.put("password", password)

                    //collection명은 "emailusers"로 지정 [ RDNMS의 테이블명 같은 역할 ] //document 명이 랜덤하게 만들어짐.
                    db.collection("emailUsers").add(user).addOnSuccessListener {

                        AlertDialog.Builder(this).setMessage("회원가입 성공🙌")
                            .setPositiveButton("확인", object : DialogInterface.OnClickListener{
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    finish()
                                }

                            }).create().show()

                    }.addOnFailureListener {
                        Toast.makeText(this, "회원가입에 오류가 발생 했습니다😥", Toast.LENGTH_SHORT).show()
                    }

                }

            }.addOnFailureListener {
                Toast.makeText(this, "서버 상태가 불안합니다.😥", Toast.LENGTH_SHORT).show()
            }
    }
}
