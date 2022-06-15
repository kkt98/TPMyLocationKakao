package com.kkt1019.tpmylocationkakao.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkt1019.tpmylocationkakao.activites.MainActivity
import com.kkt1019.tpmylocationkakao.activites.PlaceUrlActivity
import com.kkt1019.tpmylocationkakao.databinding.FragmentPlaceListBinding
import com.kkt1019.tpmylocationkakao.databinding.FragmentPlaceMapBinding
import com.kkt1019.tpmylocationkakao.model.Place
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class PlaceMapFragment : Fragment() {

    val binding : FragmentPlaceMapBinding by lazy { FragmentPlaceMapBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    val mapView:MapView by lazy { MapView(context) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //맵뷰 콘테이너에 맵뷰 객체를 추가하기
        binding.containerMapview.addView(mapView)

        //지도관련 설정 (지도위치, 마커추가 등)
        setMapAndMarkers()

    }

    private fun setMapAndMarkers(){

        //마커 or 말풍선의 클릭이벤트에 반응하는 리스너 등록
        // 반드시 마커추가하는것보다 먼저 등록되어 있어야 동작함
        mapView.setPOIItemEventListener(markerEventListener)

        //지도 중심좌표 설정
        //현재 내위치
        val lat:Double = (activity as MainActivity).mylocation?.latitude ?: 37.5666805
        val lng:Double = (activity as MainActivity).mylocation?.longitude ?: 126.9784147

        //위도/경도를 카카오지도의 맵 좌표객체 로 생성
        var myMapPoint: MapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 5, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

        //내위치 마커 추가
        val marker = MapPOIItem()

        //마커 설정들
        marker.apply {
//            this.itemName = "me"
//            this 생략가능
            itemName="ME"
            mapPoint=myMapPoint
            markerType=MapPOIItem.MarkerType.BluePin
            selectedMarkerType=MapPOIItem.MarkerType.YellowPin
        }
        mapView.addPOIItem(marker)

//        검색결과 장소들 마커 추가
        val documents: MutableList<Place>? = (activity as MainActivity).searchPlaceResponse?.documents
        documents?.forEach {

            val point:MapPoint = MapPoint.mapPointWithGeoCoord(it.y.toDouble(), it.x.toDouble())

            //마커객체 생성
            var marker:MapPOIItem = MapPOIItem().apply {
                itemName = it.place_name
                mapPoint = point
                markerType = MapPOIItem.MarkerType.RedPin
                selectedMarkerType = MapPOIItem.MarkerType.YellowPin

                //해당 POI item(마커)와 관련된 정보를 저장하고 있는 데이터객체를 보관
                userObject = it
            }

            mapView.addPOIItem(marker)

        }

    }

    //마커나 말풍선이 클릭되는 이벤트에 반응하는 리스너 객체 생성
    private val markerEventListener: MapView.POIItemEventListener= object : MapView.POIItemEventListener{
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
            //마커가 클릭되었을때 발동하는 메소드
        }

        override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
            //deprecated 이제는 아래 오버로딩된 메소드 사용 권장
        }

        override fun onCalloutBalloonOfPOIItemTouched(
            p0: MapView?,
            p1: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {
            //마커의 말풍선 클릭했을때 발동하는 메소드
            //두번째 파라미터 p1: 마커객체

            if (p1?.userObject == null) return

            val place:Place = p1.userObject as Place

            //장소에대한 상세정보 보여주는 화면으로 이동
            val intent = Intent(context, PlaceUrlActivity::class.java)
            intent.putExtra("place_url", place.place_url)
            startActivity(intent)
        }

        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
            //마커 드래그해서 움직일때 발동
        }

    }

}