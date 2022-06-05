package shape.edu.hkmilkteaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class NotesFragment : Fragment() {

    private lateinit var newRecyclerView : RecyclerView
    private lateinit var fab : FloatingActionButton

    //val db = Firebase.firestore
    //val firestoreDB = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_notes, container, false)

        newRecyclerView = view.findViewById(R.id.recyclerViewNotes)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fab = view.findViewById(R.id.addNote)
        fab.setOnClickListener {

            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout, NoteFragment())
            transaction.commit()

        }

        return view
    }

}