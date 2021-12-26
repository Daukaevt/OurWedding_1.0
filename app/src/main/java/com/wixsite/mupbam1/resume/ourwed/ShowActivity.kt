package com.wixsite.mupbam1.resume.ourwed

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogConst
import kotlinx.android.synthetic.main.activity_new_event3.*
import kotlinx.android.synthetic.main.activity_show.*


class ShowActivity : AppCompatActivity() {
    private var tvName: TextView? = null
    private var tvSecName: TextView? = null
    private var tvEmail: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        init()
        intentMain
    }

    private fun init() {
        tvName = findViewById(R.id.tvShowFileName)
        tvSecName = findViewById(R.id.tvShowHttp)

    }

    private val intentMain: Unit
        private get() {
            val i = intent
            if (i != null) {
                tvShowFileName!!.text = i.getStringExtra(DialogConst.USER_FILE_NAME)
                tvShowHttp!!.text = i.getStringExtra(DialogConst.USER_HTTPS)

            }
        }
}