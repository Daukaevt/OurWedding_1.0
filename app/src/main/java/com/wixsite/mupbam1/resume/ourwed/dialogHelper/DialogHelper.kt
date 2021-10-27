package com.wixsite.mupbam1.resume.ourwed.dialogHelper

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.snapshot.Index
import com.wixsite.mupbam1.resume.ourwed.MainActivity
import com.wixsite.mupbam1.resume.ourwed.R
import com.wixsite.mupbam1.resume.ourwed.accountHelper.AccountHelper
import com.wixsite.mupbam1.resume.ourwed.databinding.SignDialogBinding

class DialogHelper(act:MainActivity) {
    private val act=act
    val accountHelper=AccountHelper(act)
    fun createSignDialog(index: Int){
        val builder=AlertDialog.Builder(act)
        val binding=SignDialogBinding.inflate(act.layoutInflater)
        builder.setView(binding.root)
        setDialogState(index,binding)
        val dialog=builder.create()
        binding.btRegistration.setOnClickListener {
            setOnClickRegistration(index,binding,dialog)
        }
        binding.btForgot.setOnClickListener {
            setOnClickResetPasword(binding,dialog)
        }
        binding.btGoogleSignIn.setOnClickListener {
            accountHelper.signInWithGoogle()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setOnClickResetPasword(binding: SignDialogBinding, dialog: AlertDialog?) {
        if (binding.edEmail.text.isNotEmpty()){
            act.mAuth.sendPasswordResetEmail(binding.edEmail.text.toString()).addOnCompleteListener {task->
                if (task.isSuccessful){
                    Toast.makeText(act,act.resources.getString(R.string.emailResetPasword), Toast.LENGTH_LONG).show()
                }else{

                }
            }
            dialog?.dismiss()
        }else{
            binding.tvEmailNotFound.visibility=View.VISIBLE
        }
    }

    private fun setOnClickRegistration(index: Int, binding: SignDialogBinding, dialog: AlertDialog?) {
        dialog?.dismiss()
        if (index==DialogConst.Sign_Up_State){
            accountHelper.signUpWithEmail(binding.edEmail.text.toString(), binding.edPasword.text.toString())
        }else{
            accountHelper.signInWithEmail(binding.edEmail.text.toString(), binding.edPasword.text.toString())
        }
    }

    private fun setDialogState(index: Int, binding: SignDialogBinding) {
        if (index==DialogConst.Sign_Up_State){
            binding.tvSignTitle.text=act.resources.getString(R.string.signUp)
            binding.btRegistration.text=act.resources.getString(R.string.btnReg)
        }else{
            binding.btRegistration.text=act.resources.getString(R.string.btnEnter)
            binding.tvSignTitle.text=act.resources.getString(R.string.signIn)
            binding.btForgot.visibility=View.VISIBLE
        }
    }
}