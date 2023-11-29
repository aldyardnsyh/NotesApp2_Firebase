package com.example.notesapp2_firebase.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class FirestoreNote(
    @set:Exclude @get:Exclude var documentId: String = "",
    var titleName: String = "",
    var noteName: String = "",
    @field:ServerTimestamp
    var dateName: Date? = null
)