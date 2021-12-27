package com.wixsite.mupbam1.resume.ourwed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogConst

class rcCards : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<User1>
    private lateinit var dbRefPath : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rc_cards)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<User1>()
        getMainIntent()
    }

    private fun getMainIntent() {
        var i=intent
        if (i!=null){
            var urlIntent1=i.getCharSequenceExtra(DialogConst.MainIntent)
            dbRefPath= urlIntent1.toString()
            getUserData()
        }
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference(dbRefPath)
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(User1::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerview.adapter = UserAdapter(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

