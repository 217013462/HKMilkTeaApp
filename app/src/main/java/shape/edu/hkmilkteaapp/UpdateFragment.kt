package shape.edu.hkmilkteaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

class UpdateFragment : Fragment() {

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //    }
    //}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        val editText: EditText = view.findViewById<EditText>(R.id.editTextUpdateNote)

        val args = this.arguments
        val fsData = args?.get("data")

        editText.setText(fsData.toString())

        return view
    }
}