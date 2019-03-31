package addam.com.my.chinlaicustomer.feature.statement

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.core.util.SchedulerProvider
import addam.com.my.chinlaicustomer.database.DatabaseRepository
import addam.com.my.chinlaicustomer.rest.GeneralRepository
import addam.com.my.chinlaicustomer.rest.model.StatementResponseModel
import addam.com.my.chinlaicustomer.utilities.ObservableString
import android.arch.lifecycle.ViewModel

/**
 * Created by owner on 19/03/2019
 */
class StatementViewModel(private val schedulerProvider: SchedulerProvider, private val databaseRepository: DatabaseRepository, private val appPreference: AppPreference, private val generalRepository: GeneralRepository): ViewModel() {

    var name = ObservableString("")

    init{
        name.set(appPreference.getUser().name)
    }

    fun setDummyModels(): MutableList<StatementResponseModel> {
        val models = mutableListOf<StatementResponseModel>()
        models.add(StatementResponseModel(false, "March Invoice Statment", "http://www.google.com"))
        models.add(StatementResponseModel(false, "April Invoice Statment", "http://www.google.com"))
        return models
    }

    fun getDownload(model: StatementResponseModel) {

    }

    fun getDownloads(models: MutableList<StatementResponseModel>) {

    }
}