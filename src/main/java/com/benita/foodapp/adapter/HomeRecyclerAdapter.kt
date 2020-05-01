package com.benita.foodapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benita.foodapp.R
import com.benita.foodapp.model.Food
import com.squareup.picasso.Picasso
import java.util.ArrayList
import kotlinx.android.synthetic.main.recycler_home_single_row.view.*

class HomeRecyclerAdapter(val context: Context, val itemList:ArrayList<Food>):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single_row,parent,false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val food=itemList[position]
        holder.txtResName.text=food.resName
        holder.txtPrice.text=food.costForOne
        holder.txtResRating.text=food.resRating
       /* holder.imgResImage.setImageResource(food.resImage)*/
        Picasso.get().load(food.resImage).into(holder.imgResImage)






}

class HomeViewHolder(view: View):RecyclerView.ViewHolder(view){
    val txtResName: TextView=view.findViewById(R.id.txtResName)
    val txtPrice: TextView=view.findViewById(R.id.txtPrice)
    val txtResRating: TextView=view.findViewById(R.id.txtResRating)
    val imgResImage: ImageView =view.findViewById(R.id.imgResImage)

}
}