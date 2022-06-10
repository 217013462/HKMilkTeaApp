package shape.edu.hkmilkteaapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NotesFragment : Fragment() {

    private lateinit var newRecyclerView : RecyclerView
    private lateinit var fab : FloatingActionButton
    private lateinit var noteDao: NoteDao
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: NotesAdapter


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

        noteDao = NoteDao()
        auth = Firebase.auth

        fab.setOnClickListener {

            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout, NoteFragment())
            transaction.commit()

        }

        setUpRecyclerView()
        return view
    }

    private fun setUpRecyclerView() {

        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // get the FirestoreDB collection from noteDao
        val noteCollection = noteDao.noteCollection

        // get currentUseId
        val currentUserId = auth.currentUser!!.uid

        // get notes that created by user
        val query = noteCollection.whereEqualTo("uid",currentUserId).orderBy("created", Query.Direction.DESCENDING)
        val recyclerViewOption = FirestoreRecyclerOptions.Builder<Note>().setQuery(query,Note::class.java).build()

        adapter = NotesAdapter(recyclerViewOption)
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : NotesAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                val fsText = adapter.getItem(position).text

                val bundle = Bundle()
                bundle.putString("data",fsText)

                val fragment = UpdateFragment()
                fragment.arguments = bundle
                fragmentManager?.beginTransaction()?.replace(R.id.frameLayout,fragment)?.commit()

            }
        })

        // swipe to delete note
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                adapter.deleteNote(position)
            }
        }).attachToRecyclerView(newRecyclerView)

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}