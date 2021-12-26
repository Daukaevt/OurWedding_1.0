package com.wixsite.mupbam1.resume.ourwed

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.wixsite.mupbam1.resume.ourwed.databinding.ActivityMainBinding
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogConst
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogHelper
import io.ak1.pix.helpers.toast
import kotlinx.android.synthetic.main.nav_header_mine.view.*
import java.util.jar.Manifest


open class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var tvAccount:TextView
    private val dialogHelper=DialogHelper(this)
    val mAuth=FirebaseAuth.getInstance()
    private lateinit var permisLauncher:ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerPermissionListner()
        checkCameraPermission()
        init()
        //NewEvent()
    }

    private fun checkCameraPermission() {
        when{
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED->{
                Toast.makeText(this, "Camera run", Toast.LENGTH_LONG).show()
                }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
//                Toast.makeText(this,
//                    "We need your Camera permission ", Toast.LENGTH_LONG).show()
                permisLauncher.launch(android.Manifest.permission.CAMERA)
                    }
            else->{
                permisLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private fun registerPermissionListner(){
        permisLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                Toast.makeText(this, "Camera run", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val notReg: String = getString(R.string.not_reg)
        if (item.itemId==R.id.newEvent&&binding.navView.tvAccount.text.toString()!=notReg){
            //вернуть на NewEvent
            //Log.d("MyLog","Res-${R.drawable.image1}")
            val intent=Intent(this, NewEvent3::class.java)
                .apply {
                    putExtra("userIntent", tvAccount.text.toString())
                }
            startActivity(intent)
        }else{
            Toast.makeText(this, notReg, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==DialogConst.signInRequestCode){
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account=task.getResult(ApiException::class.java)
                if (account!=null){
                    //Log.d("MyLog","Api +$account")
                    dialogHelper.accountHelper.signInFirebaseWithGoogle(account.idToken!!)
                }
            }catch(e:ApiException){
                Log.d("MyLog","Api error:${e.message}")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    fun init(){
        val iii=R.drawable.image1
        //Log.d("MyLog","image1- $iii")
        setSupportActionBar(binding.mainContent.toolbar)
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
                val intent=Intent(this,rcCards::class.java)
             /*       .apply {
                        putExtra("userIntent", tvAccount.text.toString())
                    }*/
                startActivity(intent)


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