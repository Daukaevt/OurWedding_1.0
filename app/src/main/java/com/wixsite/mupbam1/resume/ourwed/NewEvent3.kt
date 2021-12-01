package com.wixsite.mupbam1.resume.ourwed

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import android.view.View

import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_rnd.view.*


class NewEvent3 : AppCompatActivity() {
    lateinit var binding: ActivityNewEvent3Binding
    var curFile: Uri? = null
    var filename="pic"

    val imageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityNewEvent3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){

            var i=intent
            if (i!=null){
                var urlIntent1=i.getCharSequenceExtra("urlIntent")
                var httpsReferenceNameIntent1=i.getCharSequenceExtra("httpsReferenceNameIntent")
                //var imageUrlsList=i.getCharSequenceExtra("imageUrlsListIndexed")
                Glide.with(this@NewEvent3).load(urlIntent1).into(ivUser)
               Log.d("MyLog","urlIntent $urlIntent1")
                filename=httpsReferenceNameIntent1.toString()
               Log.d("MyLog","httpsReferenceNameIntent $httpsReferenceNameIntent1")
               //Log.d("MyLog","imageUrlsList: $imageUrlsList")
            }

            ivUser.setOnClickListener {
                Intent(Intent.ACTION_GET_CONTENT).also {
                    it.type = "image/*"
                    startActivityForResult(it, DialogConst.REQUEST_CODE_IMAGE_PICK)
                }
            }

            btUpload.setOnClickListener {
                if (edUserName.text.isNotEmpty()) {
                    filename=edUserName.text.toString()
                    uploadImageToStorage(filename)
                    edUserName.text.clear()
                    //val res: Resources = resources
                    //val drawable: Drawable = res.getDrawable(R.drawable.ic_menu_camera)
                    //ivUser==drawable
                }
                else {
                    android.util.Log.d("MyLog","edUserName=null")
                    android.widget.Toast.makeText(
                        this@NewEvent3, com.wixsite.mupbam1.resume.ourwed.R.string.edUserNameEmpty, android.widget.Toast.LENGTH_LONG).show()
                }
            }

            btdownload.setOnClickListener {
                downloadImage(filename)
            }

            btDelete.setOnClickListener {
                deleteImage(filename)
            }

            btImageView.setOnClickListener {
               //нужен intent на RCImageView
               startActivity(Intent(this@NewEvent3, RCImageView::class.java))
            }
        }
    }

    private fun deleteImage(filename: String)= CoroutineScope(Dispatchers.IO).launch  {

        try {
            //curFile?.let {
                imageRef.child("images/$filename").delete().await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NewEvent3, "Successfully deleted image",
                        Toast.LENGTH_LONG).show()
                }
            //}
        } catch (e: Exception) {
            Log.d("MyLog","delete3 $filename")
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
                           Log.d("MyLog","filename is $filename")
                           //filencount++
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
                    //filencountDownload++
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
}

