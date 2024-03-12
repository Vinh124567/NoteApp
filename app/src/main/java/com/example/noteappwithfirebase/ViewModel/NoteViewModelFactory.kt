package com.example.noteappwithfirebase.ViewModel



import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteappwithfirebase.Responsitory.Responsitory

class NoteViewModelFactory(val app: Application,private val responsitory: Responsitory):ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass:Class<T>): T {
        return NoteViewModel(app,responsitory) as T
    }
}