package com.wixsite.mupbam1.resume.ourwed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val toggle=ActionBarDrawerToggle(
            this,binding.drawerLayout,binding.mainContent.toolbar,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
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
                Toast.makeText(this, "Pressed signUp", Toast.LENGTH_LONG).show()
            }
            R.id.signIn->{
                Toast.makeText(this, "Pressed signIn", Toast.LENGTH_LONG).show()
            }
            R.id.signOut->{
                Toast.makeText(this, "Pressed signOut", Toast.LENGTH_LONG).show()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}