package com.example.notesapp2_firebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp2_firebase.activity.AddEditActivity
import com.example.notesapp2_firebase.databinding.ActivityMainBinding
import com.example.notesapp2_firebase.repository.NoteRepository
import com.example.notesapp2_firebase.view_models.MainActivityViewModel
import com.mancj.materialsearchbar.MaterialSearchBar

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val noteRepository by lazy { NoteRepository(this) }

    // Inisialisasi ViewModel dengan menggunakan by lazy
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this, MainActivityViewModel.Factory(application, noteRepository)).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

         binding.addNotes.setOnClickListener {
             startActivity(Intent(this, AddEditActivity::class.java))
         }

        val searchBar = binding.searchBar
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
                // Tidak ada adapter, jadi tidak ada perlu pembaruan data
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                filterNotes(text.toString())
            }

            override fun onButtonClicked(buttonCode: Int) {
                // Handle button click if needed
            }
        })
    }

    private fun filterNotes(query: String) {
        // Logika untuk memfilter dan menampilkan catatan sesuai kebutuhan
        // ...
    }
}