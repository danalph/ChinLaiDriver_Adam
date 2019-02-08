package addam.com.my.chinlaicustomer.feature.delivery

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.BaseRecyclerViewAdapter
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.StartActivityEvent
import addam.com.my.chinlaicustomer.core.event.StartActivityModel
import addam.com.my.chinlaicustomer.databinding.ActivityDeliveryDetailsBinding
import addam.com.my.chinlaicustomer.rest.model.ViewDeliveryTripResponse
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_delivery_details.*
import javax.inject.Inject

class DeliveryDetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: DeliveryDetailsViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var adapter : DeliveryDetailsAdapter

    private val documents = arrayListOf<ViewDeliveryTripResponse.Data.Document>()
    private var driverId = ""
    private var tripId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityDeliveryDetailsBinding =  DataBindingUtil.setContentView(this, R.layout.activity_delivery_details)
        binding.viewModel = viewModel
        binding.toolbarModel = ToolbarWithBackModel(getString(R.string.view_delivery_trip), true, this::onBackPressed)
        setupView()
        setupObserver()
        driverId = appPreference.getUser().id
        tripId = intent.getStringExtra(Router.Parameter.TRIP_ID.name)
        viewModel.getDeliveryDetails(driverId,tripId)
    }

    private fun setupObserver() {
        viewModel.deliveryDetailsResponse.observe(this){
            it?: return@observe
            adapter.run {
                documents.clear()
                for (item in it.data.documents.indices){
                    it.data.documents[item].position = "${item + 1}"
                }
                documents.addAll(it.data.documents)
                notifyDataSetChanged()
                viewModel.totalDestination.set("${adapter.itemCount}")
                swipe_refresh_layout.isRefreshing = false
            }
        }

        viewModel.startActivityEvent.observe(this, object : StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@DeliveryDetailsActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@DeliveryDetailsActivity, Router.getClass(data.to), data.parameters, data.hasResults)
            }

        })

        viewModel.errorResponse.observe(this){
            val snackbar = Snackbar.make(rv_destination, it.toString(), Snackbar.LENGTH_SHORT)
            val tv = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
            tv.setTextColor(resources.getColor(R.color.white))
            snackbar.show()
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun setupView() {
        rv_destination.layoutManager = LinearLayoutManager(this)
        rv_destination.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = DeliveryDetailsAdapter(documents, R.layout.delivery_details_adapter_layout,object: BaseRecyclerViewAdapter.OnItemClickListener<ViewDeliveryTripResponse.Data.Document>{
            override fun onItemClick(item: ViewDeliveryTripResponse.Data.Document, view: View) {
                viewModel.startActivity(tripId, item.type, item.id)
            }

        })
        rv_destination.adapter = adapter
        rv_destination.isFocusable = false
        configureTabLayout()
        swipe_refresh_layout.setOnRefreshListener {
            viewModel.getDeliveryDetails(driverId,tripId)
        }
    }

    private fun configureTabLayout() {
        tab_layout.addTab(tab_layout.newTab().setText(getString(R.string.delivery_all)))
        tab_layout.addTab(tab_layout.newTab().setText(getString(R.string.delivery_pending)))
        tab_layout.addTab(tab_layout.newTab().setText(getString(R.string.delivery_completed)))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                adapter.item = getSortedItem(p0?.text.toString().toLowerCase())
                adapter.notifyDataSetChanged()
                viewModel.totalDestination.set("${adapter.itemCount}")
            }

        })
    }

    private fun getSortedItem(text: CharSequence?): ArrayList<ViewDeliveryTripResponse.Data.Document> {
        var filteredItem = ArrayList<ViewDeliveryTripResponse.Data.Document>()
        when(text){
            "all" ->{
                filteredItem = documents
            }
            "pending" ->{
                for (trip in documents){
                    if(trip.status == "2"){
                        filteredItem.add(trip)
                    }
                }
            }
            "completed" ->{
                for (trip in documents){
                    if(trip.status == "1"){
                        filteredItem.add(trip)
                    }
                }
            }
        }
        return filteredItem
    }
}
