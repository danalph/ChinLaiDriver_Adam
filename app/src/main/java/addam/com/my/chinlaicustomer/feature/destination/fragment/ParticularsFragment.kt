package addam.com.my.chinlaicustomer.feature.destination.fragment

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseFragment
import addam.com.my.chinlaicustomer.databinding.FragmentParticularsBinding
import addam.com.my.chinlaicustomer.feature.destination.ParticularsAdapter
import addam.com.my.chinlaicustomer.rest.model.ListParticulars
import addam.com.my.chinlaicustomer.rest.model.Particular
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ParticularsFragment : BaseFragment() {
    lateinit var model: List<Particular>

    lateinit var binding: FragmentParticularsBinding
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val lists: ListParticulars = it.getParcelable("particular")!!
            model = lists.list
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_particulars, container, false)
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView = binding.viewRecycle
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ParticularsAdapter(model)

    }

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
        fun newInstance(models: List<Particular>) =
            ParticularsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("particular", ListParticulars(models))
                }
            }
    }
}
