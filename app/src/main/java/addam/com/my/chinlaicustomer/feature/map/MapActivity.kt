package addam.com.my.chinlaicustomer.feature.map

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.databinding.ActivityMapBinding
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import android.app.AlertDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import javax.inject.Inject

class MapActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    @Inject
    lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding: ActivityMapBinding= DataBindingUtil.setContentView(this, R.layout.activity_map)
        binding.viewModel= viewModel
        binding.toolbarModel = ToolbarWithBackModel(getString(R.string.title_activity_map),true, this::onBackPressed)
        binding.setLifecycleOwner(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupEvents() {
        viewModel.itemModel.type.set(intent.getStringExtra(Router.Parameter.TYPE.name))
        viewModel.itemModel.itemId.set(intent.getStringExtra(Router.Parameter.DOC_NUM.name))
        viewModel.itemModel.address.set(intent.getStringExtra(Router.Parameter.ADDRESS.name))
        val status = intent.getStringExtra(Router.Parameter.STATUS.name)
        viewModel.itemModel.isComplete.set(status=="1")

        if(intent.getStringExtra(Router.Parameter.LATITUDE.name).isNotEmpty()){
            val latitude: Double = intent.getStringExtra(Router.Parameter.LATITUDE.name).toDouble()
            val longitude: Double = intent.getStringExtra(Router.Parameter.LONGITUDE.name).toDouble()
            val newLocation = LatLng(latitude, longitude)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(newLocation).title(intent.getStringExtra(Router.Parameter.COMPANY.name)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 18f))
            mMap.setOnMarkerClickListener {
                startNavigation(newLocation)
                true
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.setOnMarkerClickListener (object: GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {
                startNavigation(sydney)
                return true
            }

        })
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f))
        setupEvents()
    }

    private fun startNavigation(latLng: LatLng) {
        val latitude = latLng.latitude.toString()
        val longitude = latLng.longitude.toString()
        val b = AlertDialog.Builder(this)
        b.setTitle(R.string.select_navigation)
        val types = arrayOf(getString(R.string.google_maps), getString(R.string.waze))
        b.setItems(types) { _, i ->
            when (i) {
                1 -> {
                    val wazeUri = "waze://?ll=$latitude, $longitude&navigate=yes"
                    packageManager.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wazeUri))
                        intent.resolveActivity(it)?.let {
                            startActivity(intent)
                        }?: run{
                            Toast.makeText(this@MapActivity, R.string.no_waze_found, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                0 -> {
                    val GoogleUri = "http://maps.google.com/maps?daddr=$latitude,$longitude"
                    packageManager.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GoogleUri))
                        intent.resolveActivity(it)?.let {
                            startActivity(intent)
                        }?: run{
                            Toast.makeText(this@MapActivity, R.string.no_map_found, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        b.show()
    }
}
