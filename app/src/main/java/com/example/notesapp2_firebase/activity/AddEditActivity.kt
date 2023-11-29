package com.example.notesapp2_firebase.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp2_firebase.MainActivity
import com.example.notesapp2_firebase.databinding.ActivityAddEditBinding
import com.example.notesapp2_firebase.model.FirestoreNote
import com.example.notesapp2_firebase.repository.NoteRepository
import java.util.Date

class AddEditActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAddEditBinding.inflate(layoutInflater)
    }

    private lateinit var noteRepository: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Inisialisasi NoteRepository
        noteRepository = NoteRepository(this)

        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        if (intent.hasExtra("NOTE_EDIT")) {
            val noteId = intent.getStringExtra("NOTE_EDIT")
            if (noteId != null) {
                // Mengamati perubahan pada data catatan berdasarkan ID
                noteRepository.getNoteById(noteId).observe(this) { note ->
                    if (note != null) {
                        // Mengisi widget dengan data catatan yang ada
                        binding.title.setText(note.titleName)
                        binding.disp.setText(note.noteName)

                        binding.saveNotes.setOnClickListener {
                            val title = binding.title.text.toString()
                            val disp = binding.disp.text.toString()

                            if (title.isNotEmpty() && disp.isNotEmpty()) {
                                val note = FirestoreNote(
                                    titleName = title,
                                    noteName = disp,
                                    dateName = Date()
                                )
                                noteRepository.insert(note)
                                showToast("Note added successfully")
                                finish()
                            } else {
                                showToast("Please Enter Title & Description")
                            }
                        }
                    } else {
                        showToast("Note not found")
                        finish()
                    }
                }

            } else {
                showToast("Invalid note ID")
                finish()
            }
        } else {
            binding.saveNotes.setOnClickListener {
                val title = binding.title.text.toString()
                val disp = binding.disp.text.toString()

                if (title.isNotEmpty() && disp.isNotEmpty()) {
                    val note = FirestoreNote(
                        titleName = title,
                        noteName = disp,
                        dateName = Date()
                    )
                    noteRepository.insert(note)
                    showToast("Note added successfully")
                    finish()
                } else {
                    showToast("Please Enter Title & Description")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}