package com.kkt1019.tpmylocationkakao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkt1019.tpmylocationkakao.activites.MainActivity
import com.kkt1019.tpmylocationkakao.adapters.PlaceListRecyclerAdapter
import com.kkt1019.tpmylocationkakao.databinding.FragmentPlaceListBinding

class PlaceListFragment : Fragment() {

    val binding : FragmentPlaceListBinding by lazy { FragmentPlaceListBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPlaceListRecyclerAdapter()
    }

    private fun setPlaceListRecyclerAdapter(){

        val ma:MainActivity = activity as MainActivity

        //아직 MainActivity의 파싱작업이 완료되지 않았다면 데이터가 없음
        if (ma.searchPlaceResponse == null) return

        binding.recyclerview.adapter = PlaceListRecyclerAdapter(requireContext(), ma.searchPlaceResponse!!.documents)

    }

}