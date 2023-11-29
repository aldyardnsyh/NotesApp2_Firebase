package com.example.notesapp2_firebase.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp2_firebase.model.FirestoreNote
import com.example.notesapp2_firebase.repository.NoteRepository

class MainActivityViewModel(application: Application, private val noteRepo: NoteRepository) : AndroidViewModel(application) {

    val notesList = noteRepo.notesList

    // Pembaruan data
    fun updateNote(noteId: String, updatedNote: FirestoreNote) {
        noteRepo.updateNote(noteId, updatedNote)
    }

    // Penghapusan data
    fun deleteNote(noteId: String) {
        noteRepo.deleteNote(noteId)
    }

    override fun onCleared() {
        noteRepo.onCleared()
        super.onCleared()
    }

    // Factory untuk membuat instance MainActivityViewModel dengan parameter
    class Factory(private val application: Application, private val noteRepo: NoteRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(application, noteRepo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}