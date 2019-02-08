package addam.com.my.chinlaicustomer.feature.destination.fragment


import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.DialogImageBinding
import addam.com.my.chinlaicustomer.databinding.FragmentPhotosBinding
import addam.com.my.chinlaicustomer.feature.destination.PhotosAdapter
import addam.com.my.chinlaicustomer.rest.model.Image
import addam.com.my.chinlaicustomer.rest.model.ListImages
import addam.com.my.chinlaicustomer.widgets.GridSpacingItemDecoration
import android.app.AlertDialog
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso


/**
 * A simple [Fragment] subclass.
 * Use the [PhotosFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PhotosFragment : Fragment(), PhotosAdapter.OnItemClickListener {
    override fun onItemClick(item: Image) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val inflater = layoutInflater

        val dialogImageBinding: DialogImageBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_image, null, false)

        Picasso.get()
            .load(item.path)
            .placeholder(R.drawable.progress_animation)
            .into(dialogImageBinding.img)
        dialogImageBinding.btnCancel.setOnClickListener { dialog.dismiss() }
        builder.setView(dialogImageBinding.root)
        dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.show()
    }

    private lateinit var dialog: Dialog
    lateinit var model: List<Image>
    lateinit var binding: FragmentPhotosBinding
    lateinit var recyclerView: RecyclerView
    lateinit var photosAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val lists: ListImages = it.getParcelable("images")!!
            model = lists.list
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_photos, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView = binding.viewPhotoRecycler
        recyclerView.layoutManager = GridLayoutManager(context, 4)
        val spacing = resources.getDimensionPixelSize(R.dimen.space_small_8dp)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(4, spacing, true, 0))
        photosAdapter = PhotosAdapter(model.toMutableList(), this)
        recyclerView.adapter = photosAdapter
    }

    fun updateAdapter(models: List<Image>){
        photosAdapter.models.add(models.last())
        photosAdapter.notifyDataSetChanged()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(models: List<Image>) =
            PhotosFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("images", ListImages(models))
                }
            }
    }
}
