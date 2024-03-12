package com.example.noteappwithfirebase.Fragment

import android.app.AlertDialog
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
import com.example.noteappwithfirebase.databinding.FragmentAddNoteBinding
import com.example.noteappwithfirebase.databinding.FragmentEditNoteBinding


class editNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {
    private var edtBinding : FragmentEditNoteBinding?=null
    private val binding get()=edtBinding!!
    private lateinit var notesViewModel : NoteViewModel
    private lateinit var uid:String
    private lateinit var currentNote: Note
    private val args: editNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        edtBinding=FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost:MenuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)
        notesViewModel=(activity as MainActivity).noteViewModel
        uid= notesViewModel.getCurrentUserUid().toString()
        currentNote=args.note!!
        binding.editNoteTitle.setText(currentNote.title)
        binding.editNoteDesc.setText(currentNote.desc)
        binding.editNoteFab.setOnClickListener{
            val notTitle=binding.editNoteTitle.text.toString()
            val notDesc=binding.editNoteDesc.text.toString()
            if(notTitle.isNotEmpty()){
                val note=Note(currentNote.id,notDesc,notTitle)
                notesViewModel.updateNote(note,uid!!,note.id!!)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else
                Toast.makeText(context, "Vui lòng lập tiêu đề ghi chú", Toast.LENGTH_SHORT).show()

        }
        }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Xóa ghi chú")
            setMessage("Bạn có chắc muốn xóa ghi chú này ?")
            setPositiveButton("Xóa"){_,_->
                notesViewModel.deleteNote(uid!!,currentNote.id!!)
                Toast.makeText(context, "Đã xóa ghi chú", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("Hủy",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return  when(menuItem.itemId){
            R.id.deleteMenu->{
                deleteNote()
                true
            }else->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        edtBinding=null
    }


    }
