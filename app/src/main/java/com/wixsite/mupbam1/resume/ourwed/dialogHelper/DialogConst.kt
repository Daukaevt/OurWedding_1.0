package com.wixsite.mupbam1.resume.ourwed.dialogHelper

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object DialogConst {
    const val Sign_Up_State=0
    const val Sign_In_State=1
    const val signInRequestCode=456
    const val ERROR_EMAIL_ALREADY_IN_USE="ERROR_EMAIL_ALREADY_IN_USE"
    const val ERROR_INVALID_EMAIL="ERROR_INVALID_EMAIL"
    const val ERROR_WRONG_PASSWORD="ERROR_WRONG_PASSWORD"
    const val ERROR_WEAK_PASSWORD="ERROR_WEAK_PASSWORD"
    const val ERROR_USER_NOT_FOUND="ERROR_USER_NOT_FOUND"
    const val REQUEST_CODE_IMAGE_PICK=996
    val imageRef = Firebase.storage.reference

}