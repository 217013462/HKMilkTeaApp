package shape.edu.hkmilkteaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class NoteFragment : Fragment() {

    private lateinit var editTextNote : EditText
    private lateinit var buttonAddNote : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_note, container, false)

        editTextNote = view.findViewById(R.id.editTextNote)
        buttonAddNote = view.findViewById(R.id.buttonAddNote)

        buttonAddNote.setOnClickListener {
            val note = editTextNote.text.toString()
            if(note.isNotEmpty()) {
                val transaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.frameLayout, NotesFragment())
                transaction.commit()
            } else {
                Toast.makeText(requireContext(), "Please type something before you submit", Toast.LENGTH_SHORT).show()
            }
        }

        return view

    }

}