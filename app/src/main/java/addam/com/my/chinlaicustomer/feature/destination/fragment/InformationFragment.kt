package addam.com.my.chinlaicustomer.feature.destination.fragment


import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.FragmentInformationBinding
import addam.com.my.chinlaicustomer.rest.model.Info
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 *
 */
class InformationFragment : Fragment() {
//    private var listener: InformationFragment.OnFragmentInteractionListener? = null
    lateinit var model: Info

    lateinit var binding: FragmentInformationBinding

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ParticularsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(model: Info) =
            InformationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("model", model)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            model = it.getParcelable("model")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_information, container, false)
        binding.model = model
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if (context is InformationFragment.OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }
}
