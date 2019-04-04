package addam.com.my.chinlaicustomer.feature.myorderdetail.fragment

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.FragmentOrderDriverBinding
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDriverDetailResponse
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 *
 */
class OrderDriverFragment : Fragment() {

    lateinit var model: OrderDriverDetailResponse.Data.DO
    lateinit var binding: FragmentOrderDriverBinding

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment OrderDriverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(model: OrderDriverDetailResponse.Data.DO) =
            OrderDriverFragment().apply {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_driver, container, false)
        binding.model = model
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}
