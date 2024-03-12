package com.example.noteappwithfirebase.adapter

import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappwithfirebase.Fragment.homeFragmentDirections
import com.example.noteappwithfirebase.Model.Note
import com.example.noteappwithfirebase.databinding.NoteLayoutBinding

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer



class NoteAdapter(private val uid: String): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itembinding: NoteLayoutBinding):RecyclerView.ViewHolder(itembinding.root)
    private val differCallback=object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.desc==newItem.desc &&
                    oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem==newItem
        }
    }
    val differ=AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentNote=differ.currentList[position]
        holder.itembinding.noteTitle.text=currentNote.title
        holder.itembinding.noteDesc.text=currentNote.desc
        holder.itemView.setOnClickListener {
            val direction =homeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }
}