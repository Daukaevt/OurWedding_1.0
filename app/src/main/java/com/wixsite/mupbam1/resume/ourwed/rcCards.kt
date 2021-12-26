package com.wixsite.mupbam1.resume.ourwed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class rcCards : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<User1>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rc_cards)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<User1>()
        getUserData()

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("weAre")

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




/*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityRcCardsBinding



class rcCards : AppCompatActivity() {
    lateinit var dbRef:DatabaseReference
    lateinit var userRecyclerView: RecyclerView
    lateinit var userArrayList: ArrayList<User>

    lateinit var binding: ActivityRcCardsBinding
   lateinit var auth: FirebaseAuth
    //lateinit var adapter: UserAdapter
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityRcCardsBinding.inflate(layoutInflater)
            setContentView(binding.root)
            auth = Firebase.auth
            val database = Firebase.database
            val weAreRef = database.getReference("weAre")
            var card = Cards(auth.toString(), weAreRef.toString())

            Log.d("MyLog", "card-$card")

            userRecyclerView=findViewById(R.id.rcCards)
            userRecyclerView.layoutManager=LinearLayoutManager(this)
            userRecyclerView.setHasFixedSize(true)

        userArrayList= arrayListOf<User>()
        getUserData()





            /*var user1=card.user1.toString()
            var imageName=card.imageName.toString()
            var imageHttps=card.imageHttps.toString()
            //weAreRef.child(weAreRef.push().key?:"blabla").setValue(User())
            //Log.d("MyLog","$user1,$imageName,$imageHttps")*/

            /*    *******************weAreRef.child(weAreRef.push().key?:"balbalbal")
                .setValue(User(auth.currentUser?.displayName, ))
            onChangeListener(weAreRef)
            initRcView()
    }
    private fun initRcView()= with(binding){
        adapter= UserAdapter()
        rcCards.layoutManager=LinearLayoutManager(this@rcCards)
        rcCards.adapter=adapter
    }
    private fun onChangeListener(myRef: DatabaseReference) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                with(binding){
                    var string1=snapshot.value.toString()
                    Log.d("MyLog","Cards_RTDBvalue-$string1")
                    //startActivity(Intent(this@rcCards, rcCards::class.java))
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    *************            */
        }

    private fun getUserData() {
        dbRef=FirebaseDatabase.getInstance().getReference("weAre")
        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user=userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter=UserAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class rcCards : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var user1ArrayList : ArrayList<User1>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rc_cards)

        userRecyclerview = findViewById(R.id.rcCards)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        user1ArrayList = arrayListOf<User1>()
        getUserData()

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("weAre")

        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(User1::class.java)
                        user1ArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = UserAdapter(user1ArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}
*/