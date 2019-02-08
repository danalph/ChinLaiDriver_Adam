package addam.com.my.chinlaicustomer.feature.destination

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.core.event.*
import addam.com.my.chinlaicustomer.core.util.BaseConverter
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.*
import addam.com.my.chinlaicustomer.utilities.ObservableString
import addam.com.my.chinlaicustomer.utilities.model.DestinationItemModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.github.ajalt.timberkt.Timber
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Addam on 19/1/2019.
 */
class DestinationViewModel(var appPreference: AppPreference,var generalRepository: GeneralRepository, var schedulerProvider: SchedulerProvider): ViewModel() {

    var itemModel : DestinationItemModel = DestinationItemModel()
    lateinit var destinationResponse: DestinationResponse
    lateinit var destinationCallback: DestinationCallback
    val errorResponse: MutableLiveData<Int> = MutableLiveData()

    var finishActivityEvent = FinishActivityEvent()
    var startActivityEvent = StartActivityEvent()
    val cameraClickedEvent: GenericSingleEvent = GenericSingleEvent()

    var isLoading = ObservableBoolean(false)
    var imageBase64 = ObservableString("")
    var userId: String = appPreference.getUser().id
    var tripId = ""
    var type = ""
    var docId = ""

    init {
        //getDestination()
    }

    fun callDestination(): Single<DestinationResponse> {
        return generalRepository.getDestination(userId, tripId, type, docId).compose(schedulerProvider.getSchedulersForSingle())
    }

    fun callUpdateDocument(): Single<UpdateDocumentResponse>{
        return generalRepository.updateDocument(userId, tripId, type, UpdateDocumentRequest(destinationResponse.data!!.info!!.id!!))
            .compose(schedulerProvider.getSchedulersForSingle())
    }

    fun callUploadPhoto(photo: String): Single<UploadPhotoResponse>{
        return generalRepository.uploadPhoto(userId, tripId, type, UploadPhotoRequest(destinationResponse.data!!.info!!.id!!, photo))
            .compose(schedulerProvider.getSchedulersForSingle())
    }

    fun getDestination(){
        callDestination().subscribeBy(onSuccess = {
            val isComplete: Boolean = it.data!!.info!!.status.equals("1")
            itemModel.itemId.set(it.data.info!!.docNum!!)
            itemModel.isComplete.set(isComplete)
            itemModel.date.set(it.data.info.date!!)
            itemModel.type.set(it.data.info.type)
            destinationResponse = it
            destinationCallback.updateValues(it)
        }, onError = {
            Timber.e{it.toString()}
            errorResponse.postValue(R.string.error_getting_response)
        })
    }

    fun getUpdateDestination(){
        callDestination().subscribeBy(onSuccess = {
            if(it.status!!){
                destinationCallback.updateAdapter(it)
            }
        }, onError = {
            Timber.e{it.toString()}
            errorResponse.postValue(R.string.error_getting_response)
        })
    }

    fun updateDocument() {
        callUpdateDocument().subscribeBy(onSuccess = {
            destinationCallback.successUpdate(it.status!!)
            finishActivityEvent.value = FinishActivityEventModel()
        })
    }

    fun uploadPhoto(){
        callUploadPhoto(imageBase64.get().toString()).subscribeBy(onSuccess = {
            if(it.status){
                destinationCallback.successUpload(it.status)
                getUpdateDestination()
                isLoading.set(false)
            }else{
                Timber.e{it.message}
            }
        },onError = {
            Timber.e{it.toString()}
            isLoading.set(false)
            errorResponse.postValue(R.string.error_getting_response)
        })
    }

    fun onCameraClicked(){
        cameraClickedEvent.value = true
    }

    fun onMapClicked(){
        startActivityEvent.value = StartActivityModel(Router.Destination.MAP, hashMapOf(
            Pair(Router.Parameter.TYPE, destinationResponse.data!!.info!!.type),
            Pair(Router.Parameter.DOC_NUM, destinationResponse.data!!.info!!.docNum),
            Pair(Router.Parameter.STATUS, destinationResponse.data!!.info!!.status),
            Pair(Router.Parameter.COMPANY, destinationResponse.data!!.info!!.company),
            Pair(Router.Parameter.ADDRESS, destinationResponse.data!!.info!!.address),
            Pair(Router.Parameter.LATITUDE, destinationResponse.data!!.info!!.latitude),
            Pair(Router.Parameter.LONGITUDE, destinationResponse.data!!.info!!.longitude)
        ))
    }

    fun convertBase64(filePath: String){
        isLoading.set(true)
        Completable.fromAction {
            imageBase64.set(BaseConverter.resizeImageAndConvert(filePath, 256))
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe (object : CompletableObserver{
                override fun onComplete() {
                    uploadPhoto()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    isLoading.set(false)
                    Timber.e{e.toString()}
                }

            })
    }

    interface DestinationCallback{
        fun updateValues(model: DestinationResponse)
        fun updateAdapter(model: DestinationResponse)
        fun successUpdate(isSuccess: Boolean)
        fun successUpload(isSuccess: Boolean)
    }
}