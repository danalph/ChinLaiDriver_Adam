package addam.com.my.chinlaicustomer.feature.myorderdetail.fragment

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.FragmentOrderDeliveryStatusBinding
import addam.com.my.chinlaicustomer.rest.model.deliverydetails.OrderDeliveryStatusResponse
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_order_delivery_status.*

/**
 * A simple [Fragment] subclass.
 */
class OrderDeliveryStatusFragment : Fragment() {

    lateinit var model: List<OrderDeliveryStatusResponse.Data.POD>
    lateinit var adapter: DeliveryStatusAdapter
    lateinit var binding: FragmentOrderDeliveryStatusBinding
    lateinit var recyclerView: RecyclerView

    companion object {
        @JvmStatic
        fun newInstance(model: List<OrderDeliveryStatusResponse.Data.POD>?) =
            OrderDeliveryStatusFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("model", OrderDeliveryStatusResponse.Data.ListPOD(model!!))
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val lists: OrderDeliveryStatusResponse.Data.ListPOD = it.getParcelable("model")!!
            model = lists.list
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_order_delivery_status, container, false)
        setupView()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private fun setupView() {
        recyclerView = binding.rvDeliveryStatus
        adapter = DeliveryStatusAdapter(model)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity,  LinearLayoutManager.HORIZONTAL))
    }

}
