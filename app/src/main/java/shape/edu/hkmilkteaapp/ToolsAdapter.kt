package shape.edu.hkmilkteaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToolsAdapter (private val toolsList : ArrayList<Tools>) :
    RecyclerView.Adapter<ToolsAdapter.ToolsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolsViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tools_list,
            parent,false)

        return ToolsViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ToolsViewHolder, position: Int) {

        val currentItem = toolsList[position]
        holder.imgTools.setImageResource(currentItem.imgTools)
        holder.txtTools.text = currentItem.txtTools

    }

    override fun getItemCount(): Int {
        return toolsList.size
    }

    class ToolsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val imgTools : ImageView = itemView.findViewById(R.id.imageViewTools)
        val txtTools : TextView = itemView.findViewById(R.id.textViewTools)

    }

}