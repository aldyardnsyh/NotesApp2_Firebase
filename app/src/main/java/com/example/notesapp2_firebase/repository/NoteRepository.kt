package com.example.notesapp2_firebase.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapp2_firebase.model.FirestoreNote
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class NoteRepository(private val context: Context) {

    private val firestore = FirebaseFirestore.getInstance()
    private val notesCollection = firestore.collection("notes")

    private val _notesList = MutableLiveData<List<FirestoreNote>>()
    val notesList: LiveData<List<FirestoreNote>> get() = _notesList

    private var snapshotListener: ListenerRegistration? = null

    init {
        // Listen to changes in Firestore and update _notesList accordingly
        snapshotListener = notesCollection.addSnapshotListener { snapshot, _ ->
            val notes = snapshot?.documents?.mapNotNull { it.toObject(FirestoreNote::class.java) }
            _notesList.postValue(notes)
        }
    }

    // Fungsi untuk mendapatkan catatan berdasarkan ID
    fun getNoteById(noteId: String): LiveData<FirestoreNote?> {
        val liveData = MutableLiveData<FirestoreNote?>()

        // Retrieve data dari Firestore berdasarkan ID
        notesCollection.document(noteId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val note = documentSnapshot.toObject(FirestoreNote::class.java)
                liveData.postValue(note)
            }
            .addOnFailureListener {
                liveData.postValue(null)
            }

        return liveData
    }

    // Fungsi untuk menyimpan catatan baru ke Firestore
    fun insert(note: FirestoreNote) {
        notesCollection.add(note)
            .addOnSuccessListener {
                showToast("Note added successfully")
            }
            .addOnFailureListener {
                showToast("Failed to add note")
            }
    }

    // Pembaruan data
    fun updateNote(noteId: String, updatedNote: FirestoreNote) {
        notesCollection.document(noteId)
            .set(updatedNote)
            .addOnSuccessListener {
                // Tampilkan pesan sukses
                showToast("Note updated successfully")
            }
            .addOnFailureListener {
                // Tampilkan pesan kegagalan
                showToast("Failed to update note")
            }
    }

    // Penghapusan data
    fun deleteNote(noteId: String) {
        notesCollection.document(noteId)
            .delete()
            .addOnSuccessListener {
                // Tampilkan pesan sukses
                showToast("Note deleted successfully")
            }
            .addOnFailureListener {
                // Tampilkan pesan kegagalan
                showToast("Failed to delete note")
            }
    }

    // Pembersihan listener saat tidak digunakan
    fun clearSnapshotListener() {
        snapshotListener?.remove()
    }

    // Menutup listener Firestore saat ViewModel dihancurkan
    fun onCleared() {
        clearSnapshotListener()
    }

    // Metode utilitas untuk menampilkan Toast
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}