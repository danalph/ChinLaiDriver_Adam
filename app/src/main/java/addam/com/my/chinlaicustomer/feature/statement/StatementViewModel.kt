package addam.com.my.chinlaicustomer.feature.statement

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.StatementListResponse
import addam.com.my.chinlaicustomer.rest.model.StatementResponseModel
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by owner on 19/03/2019
 */
class StatementViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    var name = ObservableString("")
    val isLoading = ObservableBoolean(false)
    val statementListItem = MutableLiveData<ArrayList<StatementListResponse.Data.Statement>>()
    val statementUrl = MutableLiveData<String>()

    init{
        name.set(appPreference.getUser().name)
        getStatementList()
    }

    fun getStatementList(){
        generalRepository.getStatementList(appPreference.getUser().id)
            .compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy (
                onSuccess = {
                    if (it.status!!){
                        statementListItem.postValue(it.data?.statement as ArrayList<StatementListResponse.Data.Statement>?)
                    }
                },
                onError = {

                }
            )
    }

    fun getDownload(model: StatementListResponse.Data.Statement) {
        generalRepository.getStatement(model.id!!)
            .compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = {
                    if (it.status!!){
                        statementUrl.postValue(it.data?.statement?.pdf)
                    }
                },
                onError = {

                }
            )
    }
}