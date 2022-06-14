package com.kkt1019.tpmylocationkakao.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kkt1019.tpmylocationkakao.R
import com.kkt1019.tpmylocationkakao.activites.PlaceUrlActivity
import com.kkt1019.tpmylocationkakao.databinding.RecyclerItemsListFragmentBinding
import com.kkt1019.tpmylocationkakao.model.Place

class PlaceListRecyclerAdapter(val context:Context, var documents:MutableList<Place>) : RecyclerView.Adapter<PlaceListRecyclerAdapter.VH>() {

    inner class  VH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val binding:RecyclerItemsListFragmentBinding = RecyclerItemsListFragmentBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_items_list_fragment, parent, false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val place:Place = documents[position]

        holder.binding.tvPlaceName.text = place.place_name
        if (place.road_address_name == "") holder.binding.address.text = place.address_name
        else holder.binding.address.text = place.road_address_name
        holder.binding.tvDistance.text = place.distance + "m"

        //장소 아이템뷰를 클릭했을때 장소에대한 상세정보 화면으로 이동
        holder.itemView.setOnClickListener {
            val intent:Intent = Intent(context, PlaceUrlActivity::class.java)
            intent.putExtra("place_url", place.place_url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = documents.size

}