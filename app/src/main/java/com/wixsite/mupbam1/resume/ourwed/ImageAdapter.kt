package com.wixsite.mupbam1.resume.ourwed

import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.wixsite.mupbam1.resume.ourwed.databinding.ImageRndBinding

import kotlinx.android.synthetic.main.image_rnd.view.*



class ImageAdapter(val urls: List<String>, private val onClickListner: rcViewItemOnClickListner):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){


    inner class ImageViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val binding = ImageRndBinding.bind(itemView)
        fun bind(cards: Cards)= with(binding){
            ivRND.setImageResource(cards.imageId.toString().toInt())

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.image_rnd,
                parent,false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url=urls[position]
        holder.itemView.setOnClickListener {
            onClickListner.onClicked(url)
        }
        Glide.with(holder.itemView).load(url).into(holder.itemView.ivRND)
    }

    override fun getItemCount(): Int {
        return urls.size
    }
}
