package shape.edu.hkmilkteaapp

import com.google.firebase.Timestamp

data class Note(
    val text: String = "",
    val uid: String = "",
    val created: Timestamp? = null
        )