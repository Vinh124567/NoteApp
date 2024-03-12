package com.example.noteappwithfirebase.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteappwithfirebase.Model.Note
import com.example.noteappwithfirebase.Model.User
import com.example.noteappwithfirebase.Responsitory.Responsitory
import kotlinx.coroutines.launch

class NoteViewModel(app:Application,private val responsitory: Responsitory) : AndroidViewModel(app) {
    val _allUser=MutableLiveData<List<Note>>()
    val allUser:LiveData<List<Note>> =_allUser

    //User
    fun getCurrentUserUid(): String? {
        return responsitory.getCurrentUserUid()
    }
    fun loginWithEmail(email: String, password: String, callback: (Boolean) -> Unit) {
        responsitory.loginWithEmail(email, password, callback)
    }
    fun registerWithEmail(email: String, password: String, callback: (Boolean, String?) -> Unit){
        responsitory.registerWithEmail(email,password,callback)
    }

    //Note
    fun insertNote(note: Note,email: String)=viewModelScope.launch {
        responsitory.insertNote(note,email)
    }
    fun updateNote(updatedNote: Note, email: String,noteKey: String)=viewModelScope.launch {
        responsitory.updateNote(updatedNote,email,noteKey)
    }
    fun deleteNote(email: String, noteKey: String)=viewModelScope.launch {
        responsitory.deleteNote(email,noteKey)
    }
    fun loadNote(uid:String)= responsitory.loadNote(_allUser,uid)

    //SharedPreferences
    fun saveLogin(username: String, password: String){
        responsitory.saveLoginInfo(username,password)
    }
    fun loadAccount(): User? {
        return responsitory.loadAccount()
    }

    fun clearSavedLoginInfo(){
        responsitory.clearSavedLoginInfo()
    }
}