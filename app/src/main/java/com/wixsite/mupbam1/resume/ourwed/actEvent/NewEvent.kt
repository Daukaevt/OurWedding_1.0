package com.wixsite.mupbam1.resume.ourwed.actEvent

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wixsite.mupbam1.resume.ourwed.R
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityNewEventBinding
import com.wixsite.mupbam1.resume.ourwed.utils.ImagePicker
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio

class NewEvent : AppCompatActivity() {

    private lateinit var binding: ActivityNewEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}