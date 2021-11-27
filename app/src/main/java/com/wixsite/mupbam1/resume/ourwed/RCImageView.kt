package com.wixsite.mupbam1.resume.ourwed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogConst.imageRef
import kotlinx.android.synthetic.main.activity_rcimage_view.*
import kotlinx.android.synthetic.main.image_rnd.*
import kotlinx.android.synthetic.main.nav_header_mine.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext



class RCImageView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rcimage_view)

        listFiles()
    }

    fun listFiles() = CoroutineScope(Dispatchers.IO).launch  {
        try {
            val images=imageRef.child("images/").listAll().await()
            val imageUrls= mutableListOf<String>()

            for (image in images.items){
                val url=image.downloadUrl.await()
                imageUrls.add(url.toString())
            }
            withContext(Dispatchers.Main){
                val imageAdapter=ImageAdapter(imageUrls, object : rcViewItemOnClickListner{
                    override fun onClicked(url: String) {
                        val httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(url.toString())
                        Log.d("MyLog","onClicked ${httpsReference.name}")
                    }

                })
                rcImageView.apply {
                    adapter=imageAdapter
                    layoutManager= GridLayoutManager(this@RCImageView,3)

                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@RCImageView, e.message, Toast.LENGTH_LONG).show()
                val eMessage=e.message
                Log.d("MyLog","ERROR!!! $eMessage")
            }
        }
    }
}