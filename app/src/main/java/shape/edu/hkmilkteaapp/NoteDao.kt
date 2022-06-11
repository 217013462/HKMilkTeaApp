package shape.edu.hkmilkteaapp

import com.google.firebase.Timestamp.now
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NoteDao {

    // get database instance
    private val db = FirebaseFirestore.getInstance()
    // point to collection named "Notes"
    val noteCollection = db.collection("Notes")

    // get user uid from Firebase auth
    private val auth = Firebase.auth

    fun addNote(noteInputText: String) {
        val currentUserId = auth.currentUser!!.uid
        val timestamp = now()
        val note = Note(noteInputText, currentUserId, timestamp)
        noteCollection.document().set(note)

    }

    fun updateNote(noteInputText: String, fsNoteId: String) {

        val timestamp = now()
        noteCollection.document(fsNoteId).update("text", noteInputText, "updated", timestamp)

    }

}