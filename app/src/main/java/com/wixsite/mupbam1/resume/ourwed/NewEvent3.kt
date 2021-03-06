package com.wixsite.mupbam1.resume.ourwed

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityNewEvent3Binding
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityRcimageViewBinding
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogConst
import io.ak1.pix.helpers.toast
import kotlinx.android.synthetic.main.activity_new_event3.*
import kotlinx.android.synthetic.main.activity_rcimage_view.*
import kotlinx.android.synthetic.main.image_rnd.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import android.R
import android.graphics.Color
import android.view.View
import android.widget.*

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.datatransport.runtime.util.PriorityMapping.toInt
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.image_rnd.view.*
import kotlinx.coroutines.NonCancellable.start
import android.provider.Settings.Global.getString as getString1


class NewEvent3 : AppCompatActivity() {
    lateinit var binding: ActivityNewEvent3Binding

    var curFile: Uri? = null
    var filename=""
    var getPosition=0
    var userIntentAccount1=""
    var urlIntent2=""
    var httpsReferenceNameIntent2=""
    var userIntentAccount2=""
    lateinit var auth: FirebaseAuth
    val imageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityNewEvent3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val database = Firebase.database("https://ourwed-c2a46-default-rtdb.europe-west1.firebasedatabase.app")
        val weAre = database.getReference(DialogConst.WeAre)
        val acqRef = database.getReference(DialogConst.Acquaintance)
        val momentsRef = database.getReference(DialogConst.Moments)
        val vipsRef = database.getReference(DialogConst.Vips)

        with(binding){
            vis()
            setupSpinner()
            auth = Firebase.auth
            var i=intent
            if (i!=null){

                var urlIntent1=i.getCharSequenceExtra("urlIntent")
                urlIntent2=urlIntent1.toString()

                var httpsReferenceNameIntent1=i.getCharSequenceExtra("httpsReferenceNameIntent")
                httpsReferenceNameIntent2=httpsReferenceNameIntent1.toString()
                if (urlIntent1!=null){
                    ivUser.setBackgroundColor(Color.WHITE)
                    Glide.with(this@NewEvent3).load(urlIntent1).into(ivUser)
                    btDelete.visibility=View.VISIBLE
                    edUserName.visibility=View.GONE
                    btUpload.visibility=View.GONE
                    tvFileName.text=httpsReferenceNameIntent1.toString()
                    mySpinner?.visibility=View.VISIBLE
                }
                //Log.d("MyLog","urlIntent $urlIntent1")
                filename=httpsReferenceNameIntent1.toString()
            }

            ivUser.setOnClickListener {

                    Intent(Intent.ACTION_GET_CONTENT).also {
                        it.type = "image/*"
                        startActivityForResult(it, DialogConst.REQUEST_CODE_IMAGE_PICK)
                    }
                    vis()
            }

            btUpload.setOnClickListener {

                if (edUserName.text.isNotEmpty()) {
                    filename=edUserName.text.toString()
                    uploadImageToStorage(filename)
                    edUserName.text.clear()
                    //????????????, ??????????????, ???? ?????? ???????????????? ?????????? ???? ???????????? ?????? ?????????????????? ???? ????????????????
                    ivUser.setImageResource("2131230867".toInt())

                }
                else {
                    //android.util.Log.d("MyLog","edUserName=null")
                    android.widget.Toast.makeText(
                        this@NewEvent3, com.wixsite.mupbam1.resume.ourwed.R.string.edUserNameEmpty, android.widget.Toast.LENGTH_LONG).show()
                }
            }

            btdownload.setOnClickListener {
                downloadImage(filename)
            }

            btDelete.setOnClickListener {
                deleteImage(filename)
                ivUser.setImageResource("2131230867".toInt())
                tvFileName.text=""
            }

            btGallery.setOnClickListener {
                when(getPosition){
                    0-> {val intent=Intent(this@NewEvent3, RCImageView::class.java)
                        intent.putExtra("userAccountIntent", userIntentAccount1)
                        startActivity(intent)

                    }
                    1->{weAre.child(weAre.push().key?:"blabla")
                            .setValue(Cards(urlIntent2,httpsReferenceNameIntent2))
                        onChangeListener(weAre)
                    }

                    2->{acqRef.child(acqRef.push().key?:"blabla")
                        .setValue(Cards(urlIntent2,httpsReferenceNameIntent2))
                        onChangeListener(acqRef)
                    }

                    3->{momentsRef.child(momentsRef.push().key?:"blabla")
                        .setValue(Cards(urlIntent2,httpsReferenceNameIntent2))
                        onChangeListener(momentsRef)
                    }

                    4->{vipsRef.child(vipsRef.push().key?:"blabla")
                        .setValue(Cards(urlIntent2,httpsReferenceNameIntent2))
                        onChangeListener(vipsRef)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun onChangeListener(myRef: DatabaseReference) {
        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val list=ArrayList<Cards>()
                for (s in snapshot.children){
                    val card=s.getValue(Cards::class.java)
                    if (card!=null)list.add(card)
                }
                startActivity(Intent(this@NewEvent3, RCImageView::class.java))
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setupSpinner() {
        val personNames = arrayOf(
            DialogConst.eGallery,
            DialogConst.eWeAre,
            DialogConst.eAcquaintance,
            DialogConst.eMoments,
            DialogConst.eVips)
      val spinner = binding.mySpinner
       val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, personNames)
       spinner?.adapter = arrayAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
           override fun onItemSelected(
                parent: AdapterView<*>,
               view: View,
                position: Int,
                id: Long
            ) {
               getPosition= position
               when(getPosition){
                   0-> btGallery.text="Gallery"
                   1->btGallery.text=DialogConst.eWeAre
                   2->btGallery.text=DialogConst.eAcquaintance
                   3->btGallery.text=DialogConst.eMoments
                   4->btGallery.text=DialogConst.eVips
               }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
    }
    private fun vis() {
        btDelete.visibility=View.GONE
        edUserName.visibility=View.VISIBLE
        btUpload.visibility=View.VISIBLE
        tvFileName.text=""
        mySpinner.visibility=View.GONE
    }

    private fun deleteImage(filename: String)= CoroutineScope(Dispatchers.IO).launch  {

        try {
                imageRef.child("images/$filename").delete().await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NewEvent3, "Successfully deleted image",
                        Toast.LENGTH_LONG).show()
                }
        } catch (e: Exception) {
            Log.d("MyLog","deleteError $filename")
            withContext(Dispatchers.Main) {
                Toast.makeText(this@NewEvent3, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadImageToStorage(filename: String)= CoroutineScope(Dispatchers.IO).launch {

        try {
                 curFile?.let {
                     imageRef.child("images/$filename").putFile(it).await()
                       withContext(Dispatchers.Main) {
                           Toast.makeText(this@NewEvent3, "Successfully uploaded image",
                              Toast.LENGTH_LONG).show()
                       }
                 }
        } catch (e: Exception) {
                  withContext(Dispatchers.Main) {
                      Toast.makeText(this@NewEvent3, e.message, Toast.LENGTH_LONG).show()
                  }
        }
    }

    private fun downloadImage(filename: String) = CoroutineScope(Dispatchers.IO).launch{
            try {
                val maxDownloadSize = 5L * 1024 * 1024
                val bytes = imageRef.child("images/$filename").getBytes(maxDownloadSize).await()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                withContext(Dispatchers.Main) {
                    binding.ivUser.setImageBitmap(bmp)
                }
            } catch(e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NewEvent3, e.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if(resultCode == Activity.RESULT_OK && requestCode == DialogConst.REQUEST_CODE_IMAGE_PICK) {
           data?.data?.let {
               curFile = it
               binding.ivUser.setImageURI(it)
           }
         }
    }

    fun onClickSwitch(view: android.view.View) {
        val intent=Intent(this@NewEvent3, MainActivity::class.java)
        startActivity(intent)
    }
}

