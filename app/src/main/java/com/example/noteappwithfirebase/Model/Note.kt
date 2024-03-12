package com.example.noteappwithfirebase.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Note(
    var id:String?,
    val desc: String,
    val title: String
): Parcelable {
    constructor() : this("", "","")
}
