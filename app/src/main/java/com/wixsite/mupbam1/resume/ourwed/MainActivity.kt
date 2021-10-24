package com.wixsite.mupbam1.resume.ourwed

import android.accounts.Account
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityMainBinding
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogConst
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogHelper

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var tvAccount:TextView
    private val dialogHelper=DialogHelper(this)
    val mAuth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }
    fun init(){
        val toggle=ActionBarDrawerToggle(
            this,binding.drawerLayout,binding.mainContent.toolbar,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
        tvAccount=binding.navView.getHeaderView(0).findViewById(R.id.tvAccount)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.weAre->{
                Toast.makeText(this, "Pressed weAre", Toast.LENGTH_LONG).show()
            }
            R.id.acqnts->{
                Toast.makeText(this, "Pressed acqnts", Toast.LENGTH_LONG).show()
            }
            R.id.mmts->{
                Toast.makeText(this, "Pressed mmts", Toast.LENGTH_LONG).show()
            }
            R.id.vips->{
                Toast.makeText(this, "Pressed vips", Toast.LENGTH_LONG).show()
            }
            R.id.`where`->{
                Toast.makeText(this, "Pressed where", Toast.LENGTH_LONG).show()
            }
            R.id.`when`->{
                Toast.makeText(this, "Pressed when", Toast.LENGTH_LONG).show()
            }
            R.id.scnd->{
                Toast.makeText(this, "Pressed scnd", Toast.LENGTH_LONG).show()
            }
            R.id.wshs->{
                Toast.makeText(this, "Pressed wshs", Toast.LENGTH_LONG).show()
            }
            R.id.signUp->{
                dialogHelper.createSignDialog(DialogConst.Sign_Up_State)
            }
            R.id.signIn->{
                dialogHelper.createSignDialog(DialogConst.Sign_In_State)
            }
            R.id.signOut->{
                uiUpdate(null)
                mAuth.signOut()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    fun uiUpdate(user:FirebaseUser?){
        tvAccount.text=if (user==null){
            resources.getString(R.string.not_reg)
        }else{
            user.email
        }

    }
}