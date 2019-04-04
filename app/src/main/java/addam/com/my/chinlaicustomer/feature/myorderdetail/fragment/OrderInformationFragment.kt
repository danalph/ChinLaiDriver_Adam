package addam.com.my.chinlaicustomer.feature.myorderdetail.fragment

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.FragmentOrderInformationBinding
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryDetailResponse
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 */
class OrderInformationFragment : Fragment() {

    //private var listener: OnFragmentInteractionListener? = null
    lateinit var model: OrderDeliveryDetailResponse.OrderDeliveryData.DO
    lateinit var binding: FragmentOrderInformationBinding

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param model Parameter 1.
         * @return A new instance of fragment OrderInformationFragment.
         */
        @JvmStatic
        fun newInstance(model: OrderDeliveryDetailResponse.OrderDeliveryData.DO ) =
            OrderInformationFragment().apply {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_order_information, container, false)
        binding.model = model
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       /* if (context is OnFragmentInteractionListener) {
           // listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    /*interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //fun onFragmentInteraction(uri: Uri)
    }*/
}
