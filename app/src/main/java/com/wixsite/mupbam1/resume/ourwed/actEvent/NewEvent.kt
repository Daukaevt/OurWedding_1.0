package com.wixsite.mupbam1.resume.ourwed.actEvent

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.wixsite.mupbam1.resume.ourwed.R
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityNewEventBinding
import com.wixsite.mupbam1.resume.ourwed.utils.ImagePicker
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.helpers.toast
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import kotlin.coroutines.CoroutineContext

class NewEvent : AppCompatActivity() {
    lateinit var filepath:Uri
    var storage = Firebase.storage
    var storageRef = storage.reference
    val gsReference = storage.getReferenceFromUrl("gs://ourwed-c2a46.appspot.com/images/pic.jpg")




    private lateinit var binding: ActivityNewEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            btChooseImg.setOnClickListener{
                selectImage()
            }
            btUploadImg.setOnClickListener{
                uploadFile()
            }
            btDownloadFile.setOnClickListener {
                downloadImage()
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun downloadImage(){
        val storageReference = Firebase.storage.reference
        val gsReference = storage.getReferenceFromUrl("gs://ourwed-c2a46.appspot.com/images/pic.jpg")
        val token= FirebaseMessaging.getInstance().getToken()
        Log.d("MyLog","token- $token")
        var imagePath=binding.img

        //Api firebase https://firebase.google.com/docs/storage/android/download-files?authuser=0#kotlin+ktx_3
        storage = Firebase.storage
        val storageRef = storage.reference
        var downLoadUrl=
        storageRef.child("images/pic.jpg").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            var UrlString=this
            Log.d("MyLog", "URL- $UrlString")
        }.addOnFailureListener {
            // Handle any errors
            Log.w("MyLog", "ERROR")
        }

        Glide.with(this)
                    .load("https://firebasestorage.googleapis.com/v0/b/ourwed-c2a46.appspot.com/o/images%2Fpic.jpg?alt=media&token=fb030bc8-c10b-4d4d-81fb-e6bc86a0c84f")
                    .into(imagePath)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MyLog", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            val msg = getString(R.string.msg_token_fmt)
            Log.d("MyLog", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

    }

    //selectImage+onActivityResult берем картинку из галереи и выводим в img
    private fun selectImage() {
        val intent=Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent,"Choose Picture"), 997)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==997 && resultCode==Activity.RESULT_OK && data!=null){
            filepath=data.data!!


            var bitmap=MediaStore.Images.Media.getBitmap(contentResolver,filepath)

            binding.img.setImageBitmap(bitmap)
        }
    }

    //upload selected file to Firebase
    private fun uploadFile() {
        if (filepath!=null){
            var progressDialog=ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.show()
            var imageReference=FirebaseStorage.getInstance().reference.child("images/pic.jpg")
            var filepath1=imageReference.toString()
            Log.d("MyLog","path- $filepath1")

            imageReference.putFile(filepath)
                .addOnSuccessListener {p0->
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,"File Uploaded", Toast.LENGTH_LONG).show()

                }
                .addOnFailureListener {p0->
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,p0.message, Toast.LENGTH_LONG).show()

                }
                .addOnProgressListener {p0->
                    var progress:Double=(100.0*p0.bytesTransferred)/p0.bytesTransferred
                    progressDialog.setMessage("Uploaded-${progress.toInt()}%")
                }
        }
    }
}