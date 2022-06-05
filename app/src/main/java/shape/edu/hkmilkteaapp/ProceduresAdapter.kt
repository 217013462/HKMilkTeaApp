package shape.edu.hkmilkteaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProceduresAdapter (private val proceduresList : ArrayList<Procedures>) :
    RecyclerView.Adapter<ProceduresAdapter.ProceduresViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProceduresViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.procedures_list,
        parent,false)

        return ProceduresViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProceduresViewHolder, position: Int) {

        val currentItem = proceduresList[position]
        holder.imgProcedures.setImageResource(currentItem.imgProcedures)
        holder.txtProcedures.text = currentItem.txtProcedures

    }

    override fun getItemCount(): Int {
        return proceduresList.size
    }


    class ProceduresViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val imgProcedures : ImageView = itemView.findViewById(R.id.imageViewProcedures)
        val txtProcedures : TextView = itemView.findViewById(R.id.textViewProcedures)

    }

}