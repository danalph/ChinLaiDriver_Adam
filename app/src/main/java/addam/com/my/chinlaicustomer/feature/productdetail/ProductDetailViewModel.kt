package addam.com.my.chinlaicustomer.feature.productdetail

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.ProductDetailData
import addam.com.my.chinlaicustomer.utilities.observe
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt

class ProductDetailViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel(){

    var counter = ObservableInt(1)

    val detailResponse = MutableLiveData<ProductDetailData>()

    init {

        getDetail()
    }

    fun getDetail(){

        val imagesList = arrayListOf<String>()
        imagesList.add("https://image.shutterstock.com/image-photo/modern-cordless-drill-bit-on-450w-1261760791.jpg")
        imagesList.add("https://image.shutterstock.com/image-photo/cordless-drill-screwdriver-bit-on-450w-1048446001.jpg")
        imagesList.add("https://image.shutterstock.com/image-photo/side-view-isolated-cordless-power-450w-540675925.jpg")
        imagesList.add("https://image.shutterstock.com/image-photo/battery-drill-450w-55303534.jpg")

        val productDetailData = ProductDetailData("1", "Professional Electric Cordless Drill","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32."
        , "1,000.00", imagesList)

        detailResponse.postValue(productDetailData)

    }

    fun onPlusClick(){
        if (counter.get() >= 1)
            counter.set(counter.get() + 1)
    }

    fun onMinusClick(){
        if (counter.get() > 1)
            counter.set(counter.get() - 1)
    }

    fun onAddToCart(){

    }
}