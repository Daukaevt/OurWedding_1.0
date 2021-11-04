package com.wixsite.mupbam1.resume.ourwed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_rnd.view.*


class ImageAdapter(val urls:List<String>):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){


    inner class ImageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        return ImageViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.image_rnd,
                parent,false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url=urls[position]
        Glide.with(holder.itemView).load(url).into(holder.itemView.ivRND)

    }

    override fun getItemCount(): Int {
        return urls.size
    }
}
