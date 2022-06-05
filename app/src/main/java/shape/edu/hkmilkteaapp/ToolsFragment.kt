package shape.edu.hkmilkteaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ToolsFragment : Fragment() {

    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newArrayList : ArrayList<Tools>

    lateinit var toolsImageId : Array<Int>
    lateinit var toolsTextString : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolsImageId = arrayOf(
            R.drawable.t01,
            R.drawable.t02,
            R.drawable.t03,
            R.drawable.t04,
            R.drawable.t05,
            R.drawable.t06,
            R.drawable.t07,
            R.drawable.t08
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_tools, container, false)

        newRecyclerView = view.findViewById(R.id.recyclerViewTool)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        toolsTextString = resources.getStringArray(R.array.tools)

        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Tools>()
        getTools()

        return view

    }

    private fun getTools() {

        for (i in toolsImageId.indices) {

            val tools = Tools(toolsImageId[i], toolsTextString[i])
            newArrayList.add(tools)

        }

        newRecyclerView.adapter = ToolsAdapter(newArrayList)

    }

}

