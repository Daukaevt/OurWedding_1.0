package com.wixsite.mupbam1.resume.ourwed.dialogHelper

import android.app.AlertDialog
import com.google.firebase.database.snapshot.Index
import com.wixsite.mupbam1.resume.ourwed.MainActivity
import com.wixsite.mupbam1.resume.ourwed.R
import com.wixsite.mupbam1.resume.ourwed.accountHelper.AccountHelper
import com.wixsite.mupbam1.resume.ourwed.databinding.SignDialogBinding

class DialogHelper(act:MainActivity) {
    private val act=act
    private val accountHelper=AccountHelper(act)
    fun createSignDialog(index: Int){
        val builder=AlertDialog.Builder(act)
        val binding=SignDialogBinding.inflate(act.layoutInflater)
        builder.setView(binding.root)
        if (index==DialogConst.Sign_Up_State){
            binding.tvSignTitle.text=act.resources.getString(R.string.signUp)
            binding.btRegistration.text=act.resources.getString(R.string.btnReg)
        }else{
            binding.btRegistration.text=act.resources.getString(R.string.btnEnter)
            binding.tvSignTitle.text=act.resources.getString(R.string.signIn)
        }
        val dialog=builder.create()
        binding.btRegistration.setOnClickListener {
            dialog.dismiss()
            if (index==DialogConst.Sign_Up_State){
                accountHelper.signUpWithEmail(binding.edEmail.text.toString(), binding.edPasword.text.toString())

            }else{
                accountHelper.signInWithEmail(binding.edEmail.text.toString(), binding.edPasword.text.toString())

            }

        }
        dialog.show()
    }
}