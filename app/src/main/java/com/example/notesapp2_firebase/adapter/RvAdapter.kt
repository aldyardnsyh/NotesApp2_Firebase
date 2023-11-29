package com.example.notesapp2_firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp2_firebase.databinding.SampleNotesBinding
import com.example.notesapp2_firebase.model.FirestoreNote

class RvAdapter(private val onItemClick: (FirestoreNote) -> Unit) : RecyclerView.Adapter<RvAdapter.NoteViewHolder>() {

    private var notesList: List<FirestoreNote> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = SampleNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.bind(currentNote)
        holder.itemView.setOnClickListener { onItemClick(currentNote) }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setNotes(notes: List<FirestoreNote>) {
        notesList = notes
        notifyDataSetChanged()
    }

    class NoteViewHolder(private val binding: SampleNotesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: FirestoreNote) {
            binding.title.text = note.titleName
            binding.descNotes.text = note.noteName
            // Set other views accordingly
        }
    }
}