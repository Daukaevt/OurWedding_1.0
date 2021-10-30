package com.wixsite.mupbam1.resume.ourwed.actEvent

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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
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

class NewEvent : AppCompatActivity() {
    lateinit var filepath:Uri

    private lateinit var binding: ActivityNewEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btChooseImg.setOnClickListener{
            selectImage()
        }
        binding.btUploadImg.setOnClickListener{
            uploadFile()
        }
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