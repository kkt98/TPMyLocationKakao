package com.kkt1019.tpmylocationkakao.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.auth.User
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kkt1019.tpmylocationkakao.G
import com.kkt1019.tpmylocationkakao.databinding.ActivityLoginBinding
import com.kkt1019.tpmylocationkakao.model.NaverUserInfoResponse
import com.kkt1019.tpmylocationkakao.model.NidUser
import com.kkt1019.tpmylocationkakao.model.UserAccount
import com.kkt1019.tpmylocationkakao.network.RetrofitApiService
import com.kkt1019.tpmylocationkakao.network.RetrofitHelper
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


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
        binding.layoutLoginEmail.setOnClickListener {
            startActivity(Intent(this, EmailSignInActivity::class.java))
        }

        //간편로그인 클릭
        binding.btnLoginKakao.setOnClickListener { clickLoginKakao() }
        binding.btnLoginGoogle.setOnClickListener { clickLoginGoogle() }
        binding.btnLoginNaver.setOnClickListener { clickLoginNaver() }

        //카카오 로그인 키해시값 얻어오기
        val keyHash = Utility.getKeyHash(this)
        Log.i("keyHash", keyHash)
    }

    private fun clickLoginKakao(){
        //kakao login sdk

        //카카오 로그인 성공했을때 반응하는 callback 객체 생성
        val callback : (OAuthToken?, Throwable?)->Unit = {token, error ->
            if (error != null) {
                Toast.makeText(this, "카카오 로그인 실패😥", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "카카오 로그인 성공😊", Toast.LENGTH_SHORT).show()

                //사용자의 정보 요청
                UserApiClient.instance.me { user, error ->
                    if (user != null){
                        var id:String = user.id.toString()
                        var email:String = user.kakaoAccount?.email ?:"" //앨비스 연산자
                        G.userAccount = UserAccount(id, email)

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                   }
                }
            }
        }

        //카카오톡이 설치되어 있으면 카카오톡로그인, 없으면 카카오계정 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        }else{
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun clickLoginGoogle(){

        //Firebase Authentication - ID공급업체 (google)

        //google login 관련 가이드문서를 firebase 에서 보면
        //무조건 firebase의 auth 제품과 연동하여 만들도록 안내함

        //그래서 구글로그인만 하려면 구글개발자 사이트의 가이드문서를 참고할것을 추천

        //구글 로그인 옵션객체 생성 - Builder이용
        val gso:GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        //구글 로그인 화면 액티비티를 실행시켜주는 Intent 객체 얻어오기
        val intent = GoogleSignIn.getClient(this, gso).signInIntent
        resultLauncher.launch(intent)
    }

    //새로운 액티비티를 실행하고 그 결과를 받아오는 객체를 등록하기
    val resultLauncher:ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object : ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            if (result?.resultCode == RESULT_CANCELED) return
            
            //로그인 결과를 가져온 Intent객체 소환
            val intent: Intent? = result?.data
            
            //Intent로부터 구글 계정 정보를 가져오는 작업 객체 생성 하여 결과데이터 받기
            val account:GoogleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(intent).result
            
            var id: String = account.id.toString()
            var email : String = account.email ?: ""

            Toast.makeText(this@LoginActivity, "$email", Toast.LENGTH_SHORT).show()
            G.userAccount = UserAccount(id, email)

            //main화면으로 이동
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

    })

    private fun clickLoginNaver(){

        //네이버 아이디 로그인 [네아로] - 사용자 정보를 REST API 로 받아오는 방식
        //Retrofit 네트워크 라이브러리를 사용하기

        //네이버 개발자센터의 가이드문서 참고
        //Nid - OAuth sdk 추가

        //로그인 초기화
        NaverIdLoginSDK.initialize(this,"bfbVxvTCWhipVTLDGSZF","u9NIe0AayY","내장")

        //로그인 인증 하기 - 로그인 정보를 받는게아니라 로그인정보를 받기위한 REST API의 접근 키(Token:토큰) 을 발급받기
        //이 Token으로 네트워크 API 를 통해 Json데이터를 받아 정보를 얻어오는것.
        NaverIdLoginSDK.authenticate(this, object : OAuthLoginCallback{
            override fun onError(errorCode: Int, message: String) {
                Toast.makeText(this@LoginActivity, "server error : $message", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Toast.makeText(this@LoginActivity, "로그인 실패 : $message", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()

                //사용자 정보를 가져오는 REST API의 접속토큰
                val accessToken : String? = NaverIdLoginSDK.getAccessToken()
                //Toast.makeText(this@LoginActivity, "token : $accessToken", Toast.LENGTH_SHORT).show()

                //사용자 정보 가져오는 네트워크 작업수행 (Retrofit library 이용하기)
                val retrofit = RetrofitHelper.getRetrofitInstance("https://openapi.naver.com")
                retrofit.create(RetrofitApiService::class.java).getNidUserInfo("Bearer $accessToken").enqueue( object : retrofit2.Callback<NaverUserInfoResponse>{
                    override fun onResponse(
                        call: Call<NaverUserInfoResponse>,
                        response: Response<NaverUserInfoResponse>
                    ) {
                        val userInfo: NaverUserInfoResponse? = response.body()
                        val id: String = userInfo?.response?.id ?: ""
                        val email: String = userInfo?.response?.email ?: ""

                        Toast.makeText(this@LoginActivity, "$id, $email", Toast.LENGTH_SHORT).show()
                        G.userAccount = UserAccount(id, email)

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onFailure(call: Call<NaverUserInfoResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "error $t", Toast.LENGTH_SHORT).show()
                    }

                })
            }

        })

    }

}



