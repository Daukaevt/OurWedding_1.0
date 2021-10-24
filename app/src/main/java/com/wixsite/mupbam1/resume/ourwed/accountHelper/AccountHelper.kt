package com.wixsite.mupbam1.resume.ourwed.accountHelper

import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.wixsite.mupbam1.resume.ourwed.MainActivity
import com.wixsite.mupbam1.resume.ourwed.R

class AccountHelper(act:MainActivity) {
    private val act = act
    fun signUpWithEmail(email: String, password: String) {
        Log.d("MyLog","signUpWithEmail")
        if (email.isNotEmpty()&&password.isNotEmpty()) {
            act.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result?.user!!)
                        act.uiUpdate(task.result?.user)
                        Log.d("MyLog","sendEmailVerification")
                    } else {
                        Log.d("MyLog","else")
                        Toast.makeText(act,act.resources.getString(R.string.signUpEror),Toast.LENGTH_LONG).show()
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
                }
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
}