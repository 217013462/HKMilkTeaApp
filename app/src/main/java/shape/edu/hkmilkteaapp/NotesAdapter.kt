package shape.edu.hkmilkteaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class NotesAdapter(options: FirestoreRecyclerOptions<Note>) : FirestoreRecyclerAdapter<Note,NotesAdapter.NotesViewHolder>(
    options
) {

    private lateinit var notesListener : onItemClickListener

    interface onItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        notesListener = listener

    }

    class NotesViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val textViewNotes: TextView = itemView.findViewById(R.id.textViewNotes)

        init {

            itemView.setOnClickListener{

                listener.onItemClick(absoluteAdapterPosition)

            }

        }

    }

    fun deleteNote(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notes_list,parent,false)

        return NotesViewHolder(itemView, notesListener)

    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int, model: Note) {

        holder.textViewNotes.text = model.text

    }

}