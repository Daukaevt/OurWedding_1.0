package com.wixsite.mupbam1.resume.ourwed

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wixsite.mupbam1.resume.ourwed.databinding.CardsListItemBinding

class UserAdapter: ListAdapter<User, UserAdapter.ItemHolder>(ItemComparator()) {
    class ItemHolder(private val binding: CardsListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user: User)= with(binding){
            tvUserCards.text=user.name
            ivCard.text=user.https
            tvFileNameCards.text=user.message
        }
        companion object{
            fun create(parent:ViewGroup):ItemHolder{
                return ItemHolder(CardsListItemBinding
                    .inflate(LayoutInflater.from(parent.context),parent,false))

            }
        }
    }
    class ItemComparator:DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}