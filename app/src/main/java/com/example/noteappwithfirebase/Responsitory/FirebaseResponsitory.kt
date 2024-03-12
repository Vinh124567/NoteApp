package com.example.noteappwithfirebase.Responsitory

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteappwithfirebase.Model.Note
import com.example.noteappwithfirebase.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseResponsitory private constructor(context: Context) {
    private val databaseReference: DatabaseReference= FirebaseDatabase.getInstance().getReference("Note")
    private val firebaseAuth = FirebaseAuth.getInstance()


    companion object {
        private var instance: FirebaseResponsitory? = null
        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: FirebaseResponsitory(context).also { instance = it }
        }
    }

    fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun loginWithEmail(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun registerWithEmail(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid
                    callback(true, uid)
                } else {
                    callback(false, null)
                }
            }
    }


    suspend fun insertNote(note: Note, uid: String):String {
        val noteReference = instance?.databaseReference?.child(uid)
        val noteKey = noteReference?.push()?.key
        note.id=noteKey
        noteKey?.let {
            noteReference.child(it).setValue(note)
        }
        return noteKey!!
    }

    suspend fun updateNote( updatedNote: Note, email: String,noteKey:String){
        val noteReference = instance?.databaseReference?.child(email)?.child(noteKey)
        if (noteReference != null) {
            noteReference.setValue(updatedNote)
                .addOnSuccessListener {
                    println("Cập nhập thành công")
                }
                .addOnFailureListener { e ->
                    println("Cập nhập thất bại: ${e.message}")
                }
        } else {
            println("Không tìm thấy ghi chú")
        }
    }


    // Xóa ghi chú từ Firebase Realtime Database
    suspend fun deleteNote(email: String, noteKey: String) {
        val database = FirebaseDatabase.getInstance()
        val noteRef = database.getReference("Note").child(email).child(noteKey)
        noteRef.removeValue()
    }


    fun loadNote(noteList: MutableLiveData<List<Note>>,uid:String) {
        val database = FirebaseDatabase.getInstance()
        val notesRef = database.getReference("Note/$uid")
        notesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val notes = mutableListOf<Note>()
                for (noteSnapshot in dataSnapshot.children) {
                    if (noteSnapshot.hasChild("desc") && noteSnapshot.hasChild("title")) {
                        val note = noteSnapshot.getValue(Note::class.java)
                        note?.let {
                            notes.add(it)
                        }
                    }
                }

                noteList.postValue(notes)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                noteList.postValue(null)
            }
        })
    }


}
