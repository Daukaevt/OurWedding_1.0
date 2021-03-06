package com.wixsite.mupbam1.resume.ourwed.dialogHelper

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object DialogConst {
    const val Sign_Up_State=0
    const val Sign_In_State=1
    const val AccountConst=10
//    const val httpsReferenceNameIntentCode=11


    const val signInRequestCode=456
    const val ERROR_EMAIL_ALREADY_IN_USE="ERROR_EMAIL_ALREADY_IN_USE"
    const val ERROR_INVALID_EMAIL="ERROR_INVALID_EMAIL"
    const val ERROR_WRONG_PASSWORD="ERROR_WRONG_PASSWORD"
    const val ERROR_WEAK_PASSWORD="ERROR_WEAK_PASSWORD"
    const val ERROR_USER_NOT_FOUND="ERROR_USER_NOT_FOUND"
    const val REQUEST_CODE_IMAGE_PICK=996
    val imageRef = Firebase.storage.reference

    //Event3
    const val eGallery="Галерея"
    const val eWeAre="Это мы!"
    const val eAcquaintance="Как мы познакомились"
    const val eMoments="Памятные моменты"
    const val eVips="Те, кто очень важен для нас"

    //ShowActivity
    const val USER_FILE_NAME="USER_FILE_NAME"
    const val USER_HTTPS="USER_HTTPS"

    //for intents
    const val MainIntent="MainIntent"
    const val Gallery="gallery"
    const val WeAre="weAre"
    const val Acquaintance="acquaintance"
    const val Moments="moments"
    const val Vips="vips"





}