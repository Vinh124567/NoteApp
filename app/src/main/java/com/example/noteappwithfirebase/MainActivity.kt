package com.example.noteappwithfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.noteappwithfirebase.Responsitory.FirebaseResponsitory
import com.example.noteappwithfirebase.Responsitory.Responsitory
import com.example.noteappwithfirebase.Responsitory.SharedPreferencesResponsitory
import com.example.noteappwithfirebase.ViewModel.NoteViewModel
import com.example.noteappwithfirebase.ViewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    setupViewModel()
    }
    private fun setupViewModel(){
        val responsitory= Responsitory(FirebaseResponsitory(this), SharedPreferencesResponsitory(this))
        val viewModelProviderFactory= NoteViewModelFactory(application,responsitory)
        noteViewModel   = ViewModelProvider(this,viewModelProviderFactory)[NoteViewModel::class.java]
    }
}