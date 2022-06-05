package shape.edu.hkmilkteaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProceduresFragment : Fragment() {

    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newArrayList : ArrayList<Procedures>

    lateinit var proceduresImageId : Array<Int>
    lateinit var proceduresTextString : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        proceduresImageId = arrayOf(
            R.drawable.p01,
            R.drawable.p02,
            R.drawable.p03,
            R.drawable.p04,
            R.drawable.p05,
            R.drawable.p06,
            R.drawable.p07
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_procedures, container, false)

        newRecyclerView = view.findViewById(R.id.recyclerViewProcedure)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        proceduresTextString = resources.getStringArray(R.array.procedures)

        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Procedures>()
        getProcedures()

        return view
    }

    private fun getProcedures() {

        for (i in proceduresImageId.indices) {

            val procedures = Procedures(proceduresImageId[i], proceduresTextString[i])
            newArrayList.add(procedures)

        }

        newRecyclerView.adapter = ProceduresAdapter(newArrayList)

    }


}