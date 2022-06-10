package com.kkt1019.tpmylocationkakao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkt1019.tpmylocationkakao.databinding.FragmentPlaceListBinding
import com.kkt1019.tpmylocationkakao.databinding.FragmentPlaceMapBinding

class PlaceMapFragment : Fragment() {

    val binding : FragmentPlaceMapBinding by lazy { FragmentPlaceMapBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}