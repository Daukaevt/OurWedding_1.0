package com.wixsite.mupbam1.resume.ourwed

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_show.view.*
import kotlinx.android.synthetic.main.image_rnd.view.*

class UserAdapter(private val userList : ArrayList<User1>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_show,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]
        holder.imageName.text = currentitem.imageName
        holder.imageHttps.text = currentitem.imageHttps
        Glide.with(holder.itemView).load(holder.imageName.text).into(holder.itemView.ivPic)
        //Log.d("MyLog","holder.imageHttps.text----${holder.imageName.text}")
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageName: TextView = itemView.findViewById(R.id.tvShowFileName)
        val imageHttps : TextView = itemView.findViewById(R.id.tvShowHttp)
    }
}


