package addam.com.my.chinlaicustomer.feature.destination

import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.databinding.PhotoRowItemBinding
import addam.com.my.chinlaicustomer.rest.model.Image
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso

/**
 * Created by Addam on 21/1/2019.
 */
class PhotosAdapter(var models: MutableList<Image>, var onItemClickListener: OnItemClickListener): RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    class PhotoViewHolder: RecyclerView.ViewHolder{

        var mBinding: PhotoRowItemBinding
        constructor(mBinding: PhotoRowItemBinding) : super(mBinding.root) {
            this.mBinding = mBinding
        }

    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(p0.context)
        }

        val binding: PhotoRowItemBinding= DataBindingUtil.inflate(layoutInflater!!, R.layout.photo_row_item, p0, false)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, p1: Int) {
        Picasso.get()
            .load(models[p1].path)
            .placeholder(R.drawable.progress_animation)
            .fit()
            .into(holder.mBinding.imgPhoto)

        val item = getItemForPosition(p1)
        if (onItemClickListener != null){
            holder.mBinding.imgPhoto.setOnClickListener({onItemClickListener.onItemClick(item)})
        }
    }

    fun getItemForPosition(position: Int): Image{
        return models.get(position)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Image)
    }
}