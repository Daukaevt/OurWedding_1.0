package com.wixsite.mupbam1.resume.ourwed.actEvent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wixsite.mupbam1.resume.ourwed.R
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityNewEventBinding

class NewEvent : AppCompatActivity() {
    private lateinit var binding: ActivityNewEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}