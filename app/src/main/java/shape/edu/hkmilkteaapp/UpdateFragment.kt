package shape.edu.hkmilkteaapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.DocumentId

class UpdateFragment : Fragment() {

    private lateinit var noteDao: NoteDao

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //    }
    //}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        val editTextUpdateNote = view.findViewById<EditText>(R.id.editTextUpdateNote)
        val buttonUpdateNote = view.findViewById<Button>(R.id.buttonUpdateNote)
        val buttonCancelUpdate = view.findViewById<Button>(R.id.buttonCancelUpdate)
        noteDao = NoteDao()

        //val editText: EditText = view.findViewById<EditText>(R.id.editTextUpdateNote)

        val args = this.arguments
        val fsData = args?.get("data")
        val fsNoteId = args?.get("noteId")

        editTextUpdateNote.setText(fsData.toString())


        buttonUpdateNote.setOnClickListener {

            val note = editTextUpdateNote.text.toString()

            if(note.isNotEmpty()) {

                hideKeyboard()

                // using Note data class and NoteDao to update note to Firebase
                noteDao.updateNote(note, fsNoteId as String)

                // navigate back to Notes Fragment
                val transaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.frameLayout, NotesFragment())
                transaction.commit()

            } else {
                // warn user to input something before submitting the note
                Toast.makeText(requireContext(), "Please type something before you submit", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancelUpdate.setOnClickListener {
            hideKeyboard()

            // navigate back to Notes Fragment
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout, NotesFragment())
            transaction.commit()

        }



        return view
    }

    private fun hideKeyboard() {
        if (view != null) {
            val hide = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(requireView().windowToken,0)
        }
    }
}