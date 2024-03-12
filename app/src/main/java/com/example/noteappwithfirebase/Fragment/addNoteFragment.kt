package com.example.noteappwithfirebase.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteappwithfirebase.MainActivity
import com.example.noteappwithfirebase.Model.Note
import com.example.noteappwithfirebase.R
import com.example.noteappwithfirebase.ViewModel.NoteViewModel
import com.example.noteappwithfirebase.adapter.NoteAdapter
import com.example.noteappwithfirebase.databinding.FragmentAddNoteBinding


class addNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {
    private var addBinding :FragmentAddNoteBinding?=null
    private val binding get()=addBinding!!
    lateinit var uid:String
    private lateinit var notesViewModel : NoteViewModel
    private lateinit var addNoteView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       addBinding=FragmentAddNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)
        addNoteView=view
        notesViewModel=(activity as MainActivity).noteViewModel
        uid=notesViewModel.getCurrentUserUid().toString()
    }

    private fun saveNote(view:View){
        val noteTitle =binding.addNoteTitle.text.toString().trim()
        val noteDesc=binding.addNoteDesc.text.toString().trim()
        if(noteTitle.isNotEmpty()){
            val note= Note(null,noteDesc,noteTitle)
            notesViewModel.insertNote(note,uid)
            Toast.makeText(addNoteView.context,"Note Save",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }else{
            Toast.makeText(addNoteView.context,"Please enter note title",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu->{
                saveNote(addNoteView)
                true
            }
            else->false
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        addBinding=null
    }

}