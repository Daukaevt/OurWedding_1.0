package com.wixsite.mupbam1.resume.ourwed.accountHelper

import android.media.session.MediaSession
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.wixsite.mupbam1.resume.ourwed.MainActivity
import com.wixsite.mupbam1.resume.ourwed.R
import com.wixsite.mupbam1.resume.ourwed.dialogHelper.DialogConst

class AccountHelper(act:MainActivity) {
    private val act = act
    private lateinit var googleSignInClient: GoogleSignInClient
    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty()&&password.isNotEmpty()) {
            act.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result?.user!!)
                        act.uiUpdate(task.result?.user)
                    } else {
                        Toast.makeText(act,act.resources.getString(R.string.signUpEror),Toast.LENGTH_LONG).show()
                        //Log.d("MyLog","Exception: ${exception.errorCode}")
                        Log.d("MyLog","Exception: ${task.exception}")
                        if (task.exception is FirebaseAuthUserCollisionException){
                            val exception=task.exception as FirebaseAuthUserCollisionException
                            if (exception.errorCode==DialogConst.ERROR_EMAIL_ALREADY_IN_USE){
                                Toast.makeText(act, "ERROR_EMAIL_ALREADY_IN_USE", Toast.LENGTH_LONG).show()
                            }
                        }else if (task.exception is FirebaseAuthInvalidCredentialsException){
                            val exception=task.exception as FirebaseAuthInvalidCredentialsException
                            if (exception.errorCode==DialogConst.ERROR_INVALID_EMAIL){
                                Toast.makeText(act, "ERROR_INVALID_EMAIL", Toast.LENGTH_LONG).show()
                            }

                        }
                    }
            }
        }
    }
    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty()&&password.isNotEmpty()) {
            act.mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
                if (task.isSuccessful) {
                    act.uiUpdate(task.result?.user)
                } else {
                    Toast.makeText(act,act.resources.getString(R.string.signInEror),Toast.LENGTH_LONG).show()
                    Log.d("MyLog","Exception: ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        Log.d("MyLog","Exception: ${exception.errorCode}")
                        if (exception.errorCode == DialogConst.ERROR_WRONG_PASSWORD) {
                            Toast.makeText(act, "ERROR_WRONG_PASSWORD", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    fun signInWithGoogle(){
        googleSignInClient=getSignInClient()
        val intent=googleSignInClient.signInIntent
        act.startActivityForResult(intent, DialogConst.signInRequestCode)
    }
    fun signInFirebaseWithGoogle(token:String){
        val credential=GoogleAuthProvider.getCredential(token,null)
        act.mAuth.signInWithCredential(credential).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(act, "sign in done", Toast.LENGTH_LONG).show()
                act.uiUpdate(task.result?.user)
            }else{

            }
        }
    }
    private fun sendEmailVerification(user:FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener{task->
            if (task.isSuccessful){
                Log.d("MyLog","send successful")
                Toast.makeText(act,act.resources.getString(R.string.sendVerificationEmailDone), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(act,act.resources.getString(R.string.sendVerificationEmailError), Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun getSignInClient():GoogleSignInClient{
        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
        requestIdToken(act.getString(R.string.default_web_client_id)).requestEmail().build()
        return GoogleSignIn.getClient(act,gso)
    }
}