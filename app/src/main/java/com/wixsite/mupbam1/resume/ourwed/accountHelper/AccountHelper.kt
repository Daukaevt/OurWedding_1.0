package com.wixsite.mupbam1.resume.ourwed.accountHelper

import android.media.session.MediaSession
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
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
                                //Link Email
                                linkEmailToGoogle(email, password)
                            }
                        }
                        else if (task.exception is FirebaseAuthWeakPasswordException){
                            val exception=task.exception as FirebaseAuthWeakPasswordException
                            if (exception.errorCode==DialogConst.ERROR_WEAK_PASSWORD){
                               Toast.makeText(act, "ERROR_WEAK_PASSWORD", Toast.LENGTH_LONG).show()
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
                    }else if (task.exception is FirebaseAuthInvalidUserException) {
                        val exception = task.exception as FirebaseAuthInvalidUserException
                        //Log.d("MyLog","Exception: ${exception.errorCode}")
                        if (exception.errorCode == DialogConst.ERROR_USER_NOT_FOUND) {
                            Toast.makeText(act, "ERROR_USER_NOT_FOUND", Toast.LENGTH_LONG).show()
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
    fun signOutWithGoogle(){
        getSignInClient().signOut()
    }
    fun signInFirebaseWithGoogle(token:String){
        val credential=GoogleAuthProvider.getCredential(token,null)
        act.mAuth.signInWithCredential(credential).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(act, "sign in done", Toast.LENGTH_LONG).show()
                act.uiUpdate(task.result?.user)
            }
        }
    }
    private fun linkEmailToGoogle(email: String,password: String){
        val credential=EmailAuthProvider.getCredential(email,password)
        if (act.mAuth.currentUser!=null) {
            act.mAuth.currentUser?.linkWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(act, R.string.linkEmailToGoogle, Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(act, R.string.EnterWithGoogle,Toast.LENGTH_LONG).show()
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