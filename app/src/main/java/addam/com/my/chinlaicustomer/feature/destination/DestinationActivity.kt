package addam.com.my.chinlaicustomer.feature.destination

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.*
import addam.com.my.chinlaicustomer.databinding.ActivityDestinationBinding
import addam.com.my.chinlaicustomer.feature.destination.fragment.InformationFragment
import addam.com.my.chinlaicustomer.feature.destination.fragment.ParticularsFragment
import addam.com.my.chinlaicustomer.feature.destination.fragment.PhotosFragment
import addam.com.my.chinlaicustomer.rest.model.DestinationResponse
import addam.com.my.chinlaicustomer.utilities.ViewPagerAdapter
import addam.com.my.chinlaicustomer.utilities.model.DestinationItemModel
import addam.com.my.chinlaicustomer.utilities.model.ToolbarWithBackModel
import addam.com.my.chinlaicustomer.utilities.observe
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.FileProvider
import android.view.WindowManager
import android.widget.TextView
import com.github.ajalt.timberkt.Timber
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_destination.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DestinationActivity : BaseActivity(), DestinationItemModel.DestinationItemListener, DestinationViewModel.DestinationCallback{
    @Inject
    lateinit var viewModel: DestinationViewModel

    @Inject
    lateinit var appPreference: AppPreference

    private var imageFileName = ""

    private var pictureImagePath = ""
    private lateinit var snackbar: Snackbar
    lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val binding: ActivityDestinationBinding = DataBindingUtil.setContentView(this@DestinationActivity, R.layout.activity_destination)
        viewModel.itemModel.listener = this
        viewModel.destinationCallback = this
        binding.toolbarModel = ToolbarWithBackModel(getString(R.string.title_activity_destination),true, this::onBackPressed)
        setupEvents()
        binding.viewModel = viewModel
        viewModel.tripId = intent.getStringExtra(Router.Parameter.CATEGORY_ID.name)
        viewModel.type = intent.getStringExtra(Router.Parameter.TYPE.name)
        viewModel.docId = intent.getStringExtra(Router.Parameter.DOC_ID.name)
        viewModel.getDestination()
        setupTabs()
        setupSnackbar()
    }

    private fun setupSnackbar() {
        snackbar = Snackbar.make(main_content, "Upload Success", Snackbar.LENGTH_SHORT)
        val view = snackbar.view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(resources.getColor(R.color.black, null))
        }else view.setBackgroundColor(resources.getColor(R.color.black))
        val text = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        text.setTextColor(Color.parseColor("#ffffff"))
    }

    private fun setupEvents() {
        viewModel.finishActivityEvent.observe(this@DestinationActivity, object : FinishActivityEvent.FinishActivityObserver{
            override fun onFinishActivity(data: FinishActivityEventModel) {
                finish()
            }

            override fun onFinishActivityForResult(data: FinishActivityEventModel) {
                finish()
            }
        })
        viewModel.startActivityEvent.observe(this@DestinationActivity, object : StartActivityEvent.StartActivityObserver{
            override fun onStartActivity(data: StartActivityModel) {
                startActivity(this@DestinationActivity, Router.getClass(data.to), data.parameters, clearHistory = false)
            }

            override fun onStartActivityForResult(data: StartActivityModel) {
                startActivity(this@DestinationActivity, Router.getClass(data.to), data.parameters, clearHistory = false)
            }

        })
        viewModel.cameraClickedEvent.observe(this@DestinationActivity, object : GenericSingleEvent.EventObserver{
            override fun onPerformEvent() {
                pickImage()
            }

        })

        viewModel.errorResponse.observe(this){
            val snackbar = Snackbar.make(main_content, it.toString(), Snackbar.LENGTH_SHORT)
            val tv = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
            tv.setTextColor(resources.getColor(R.color.white))
            snackbar.show()
        }
    }

    private fun pickImage() {
        val b = AlertDialog.Builder(this)
        b.setTitle(R.string.pick_photo)
        val types = arrayOf(getString(R.string.camera))
        b.setItems(types) { _, i ->
            when (i) {
                0 -> {
                    getPermission(PermissionType.CAMERA, REQUEST_PERMISSION_CAMERA)
                }
            }
        }

        b.show()
    }

    private fun setupTabs() {
        destination_tabs.addTab(destination_tabs.newTab().setText(getString(R.string.tab_destination_1)))
        destination_tabs.addTab(destination_tabs.newTab().setText(getString(R.string.tab_destination_2)))
        destination_tabs.addTab(destination_tabs.newTab().setText(getString(R.string.tab_destination_3)))

        destination_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewpager.currentItem = p0?.position!!
            }

        })
    }

    private fun setupView(model: DestinationResponse) {
        viewpager.setPagingEnabled(false)
        adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(InformationFragment.newInstance(model.data!!.info!!), "Information")
        adapter.addFragment(ParticularsFragment.newInstance(model.data.particulars!!), "Particulars")
        adapter.addFragment(PhotosFragment.newInstance(model.data.images!!), "Photos")
        viewpager?.adapter = adapter
        viewpager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(destination_tabs))
    }

    private fun updateView(model: DestinationResponse) {
       val fragment = adapter.getItem(2) as PhotosFragment
        fragment.updateAdapter(model.data!!.images!!)
    }

    override fun runOperation(requestCode: Int) {
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> startCamera()
        }
    }

    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            try {
                val photoFile: File? = createImageFile()
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(this,
                        "my.com.chinlaicustomer.fileprovider",
                        photoFile)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            } catch (ex: IOException) {
                Timber.e(ex)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        imageFileName = "image_$timeStamp"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".png", /* suffix */
            storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        pictureImagePath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_TAKE_PHOTO -> {
//                    viewModel.imageBase64.set(BaseConverter.resizeImageAndConvert(pictureImagePath, 512))
//                    viewModel.uploadPhoto()
                    viewModel.convertBase64(pictureImagePath)
                }
            }
        }
    }

    companion object {

        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private val REQUEST_PERMISSION_CAMERA = 100
        private val REQUEST_PERMISSION_STORAGE = 101
    }

    override fun successUpload(isSuccess: Boolean) {
        if(isSuccess){
            snackbar.show()
        }else{
            snackbar.setText("Upload Failed")
            snackbar.show()
        }
    }

    override fun successUpdate(isSuccess: Boolean) {
        if(isSuccess){
            snackbar.setText("Update Successful").show()
        }else{
            snackbar.setText("Update Failed").show()
        }
    }

    override fun updateValues(model: DestinationResponse) {
        setupView(model)
    }

    override fun updateAdapter(model: DestinationResponse) {
        updateView(model)
    }

    override fun onMarkClicked() {
        val builder: AlertDialog.Builder = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            AlertDialog.Builder(this@DestinationActivity, android.R.style.Theme_Material_Light_Dialog_Alert)
        else
            AlertDialog.Builder(this@DestinationActivity)
        builder.setTitle("Update Documents")
            .setMessage(getString(R.string.alert_update_documents))
            .setPositiveButton(android.R.string.yes
            ) { p0, p1 -> viewModel.updateDocument() }
            .setNegativeButton(android.R.string.no) { p0, p1 -> }
            .show()

    }

    override fun onMapClicked() {
        viewModel.onMapClicked()
    }

    override fun onUploadClicked() {
        viewModel.onCameraClicked()
    }
}
