package com.kkt1019.tpmylocationkakao.activites

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.kkt1019.tpmylocationkakao.R
import com.kkt1019.tpmylocationkakao.databinding.ActivityMainBinding
import com.kkt1019.tpmylocationkakao.fragments.PlaceListFragment
import com.kkt1019.tpmylocationkakao.fragments.PlaceMapFragment

class MainActivity : AppCompatActivity() {

    val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    //1. 검색장소 키워드
    var searchQurey:String = "화장실" //앱 초기검색어 - 내 주변 개방된 화장실

    //2. 현재 내 위치 정보 객체(위도, 경도 정보를 멤버로 보유)
    var mylocation: Location? = null

    //TODO...
    //[Google Fused Location API 사용 : play-services-location]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //toolbar를 제목줄로 설정
        setSupportActionBar(binding.toolbar)

        //첫 실행될 프래그먼트를 동적으로 추가(첫시작을 List 형식으로)
        supportFragmentManager.beginTransaction().add(R.id.container_fragment, PlaceListFragment()).commit()

        //탭레이아웃의 탭버튼 클릭시 보여줄 프래그먼트 변경
        binding.layoutTab.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if ( tab?.text == "리스트" ){
                    supportFragmentManager.beginTransaction().replace(R.id.container_fragment, PlaceListFragment()).commit()
                }else if (tab?.text == "지도"){
                    supportFragmentManager.beginTransaction().replace(R.id.container_fragment, PlaceMapFragment()).commit()
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        //검색어 입력에 따라 장소검색하기

        binding.etSearch.setOnEditorActionListener { textView, i, keyEvent ->

            searchQurey = binding.etSearch.text.toString()
            //카카오 장소검색 API 작업요청
            searchPlaces()

            false
        }

        //특정 키워드 단축 choice 버튼들의 리스너 처리 함수 호출
        setChoiceButtonsListener()

    }

    private fun setChoiceButtonsListener(){
        binding.layoutChoice.choiceWc.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceGas.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceEv.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceMovie.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceParke.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceFood.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceFood1.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceFood2.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceFood3.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceFood4.setOnClickListener { clickChoice(it) }
        binding.layoutChoice.choiceFood5.setOnClickListener { clickChoice(it) }
    }

    //멤버변수로 기존 선택된 뷰의 id를 저장하는 변수
    var choiceId = R.id.choice_wc

    private fun clickChoice(view: View){

        //기존 선택된 뷰의 배경이미지 변경
        findViewById<ImageView>(choiceId).setBackgroundResource(R.drawable.bg_choice)

        //현재 선택된 뷰의 배경 이미지를 변경
        view.setBackgroundResource(R.drawable.bg_choice_select)

        //현재 선택한 뷰의 id를 멤버변수에 저장
        choiceId = view.id

        //선택한것에 따라서 검색장소 키워드 변경해 다시요청
        when (choiceId){

            R.id.choice_wc -> searchQurey = "화장실"
            R.id.choice_gas -> searchQurey = "주유소"
            R.id.choice_ev -> searchQurey = "전기차 충전소"
            R.id.choice_movie -> searchQurey = "영화관"
            R.id.choice_parke -> searchQurey = "공원"
            R.id.choice_food -> searchQurey = "맛집"
            R.id.choice_food1 -> searchQurey = "약국"
            R.id.choice_food2 -> searchQurey = "병원"
            R.id.choice_food3 -> searchQurey = "응급실"
            R.id.choice_food4 -> searchQurey = "맛집"
            R.id.choice_food5 -> searchQurey = "맛집"

        }
        //검색요청
        searchPlaces()

        //검색창에 글씨가 있으면 지우기
        binding.etSearch.text.clear()
        binding.etSearch.clearFocus() //이전 포커스로인해 커서가 남아있을 수 있어서 포커스 없애기

    }

    //카카오 키워드 장소검색 API작업을 수행하는 기능메소드
    private fun searchPlaces(){
        Toast.makeText(this, "$searchQurey", Toast.LENGTH_SHORT).show()

        //레트로핏을 이용하여 카카오 키워드 장소검색 API 파싱하기


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.actionbar_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

}