package com.example.noteappwithfirebase.Responsitory

import androidx.lifecycle.MutableLiveData
import com.example.noteappwithfirebase.Model.Note
import com.example.noteappwithfirebase.Model.User

class Responsitory(
    private val fb:FirebaseResponsitory,private val Sp:SharedPreferencesResponsitory
){

    //User
    fun loginWithEmail(email: String, password: String, callback: (Boolean) -> Unit){
        fb.loginWithEmail(email, password, callback)
    }
    fun registerWithEmail(email: String, password: String, callback: (Boolean, String?) -> Unit){
        fb.registerWithEmail(email,password,callback)
    }
    fun getCurrentUserUid(): String? {
        return fb.getCurrentUserUid()
    }



    //Note
    fun loadNote(NoteList: MutableLiveData<List<Note>>,uid:String)=fb.loadNote(NoteList,uid)
    suspend fun insertNote(note: Note,email: String)=fb.insertNote(note,email)
    suspend fun updateNote( updatedNote: Note, email: String,noteKey: String)=fb.updateNote(updatedNote,email,noteKey)
    suspend fun deleteNote(email: String, noteKey: String)=fb.deleteNote(email,noteKey)

    //SharedPreferences
    fun saveLoginInfo(username: String, password: String) {
        Sp.saveLogin(username, password)
    }
    fun loadAccount(): User? {
        return Sp.getSavedLoginInfo()
    }

    fun clearSavedLoginInfo(){
        Sp.clearSavedLoginInfo()
    }
}