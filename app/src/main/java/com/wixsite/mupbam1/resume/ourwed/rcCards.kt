package com.wixsite.mupbam1.resume.ourwed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityRcCardsBinding



class rcCards : AppCompatActivity() {
    lateinit var binding: ActivityRcCardsBinding
    lateinit var adapter: UserAdapter
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding= ActivityRcCardsBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val  database=Firebase.database
            val myRef=database.getReference("messages")
            myRef.child(myRef.push().key?:"blabla").setValue(User())
    }
    private fun initRcView()= with(binding){
        adapter= UserAdapter()
        rcCards.layoutManager=LinearLayoutManager(this@rcCards)
        rcCards.adapter=adapter

    }
}
