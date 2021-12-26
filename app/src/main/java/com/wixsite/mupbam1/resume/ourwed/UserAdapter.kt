package com.wixsite.mupbam1.resume.ourwed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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


    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val imageName: TextView = itemView.findViewById(R.id.tvShowFileName)
        val imageHttps : TextView = itemView.findViewById(R.id.tvShowHttp)


    }

}



/*
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
}

*/
