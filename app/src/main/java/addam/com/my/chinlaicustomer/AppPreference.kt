package addam.com.my.chinlaicustomer

import addam.com.my.chinlaicustomer.rest.model.Customers
import addam.com.my.chinlaicustomer.rest.model.SalesData
import addam.com.my.chinlaicustomer.rest.model.UserData
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject

/**
 * Created by Addam on 7/1/2019.
 */
class AppPreference() {
    companion object {
        const val FIRST_RUN = "firstRun"
        const val IS_LOGGED_IN = "isLogin"
        const val id = "id"
        const val name = "name"
        const val contact = "contact"
        const val identity = "identity"
        const val address = "address"
        const val remark = "remark"
        const val status = "status"
        const val salesId = "sales"
    }

    private lateinit var prefs: SharedPreferences

    @Inject
    constructor(context: Context): this(){
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun setLoggedIn(isLoggedIn: Boolean = false) {
        val edit =  prefs.edit()
        edit.putBoolean(IS_LOGGED_IN, isLoggedIn)
        edit.apply()
    }

    fun setUser(data: UserData){
        val edit =  prefs.edit()
        edit.putString(id, data.id)
        edit.putString(name, data.name)
        edit.putString(contact, data.contact)
        edit.putString(identity, data.identity)
        edit.putString(address, data.address)
        edit.putString(remark, data.remark)
        edit.putString(status, data.status)
        edit.apply()
    }

    fun setSales(data: SalesData){
        val edit = prefs.edit()
        edit.putString(salesId, data.id)
        edit.putString(name, data.firstName)
        edit.putString(contact, data.contact)
        edit.putString(address, data.address)
        edit.apply()
    }

    fun resetSales(){
        val edit = prefs.edit()
        edit.putString(salesId, "0")
        edit.apply()
    }

    fun logout(){
        val edit = prefs.edit()
        edit.putString(id, "")
        edit.putString(name, "")
        edit.putString(contact, "")
        edit.putString(identity, "")
        edit.putString(address, "")
        edit.putString(remark, "")
        edit.putString(status, "")
        edit.apply()
    }

    fun setCustomerId(customers: Customers){
        val edit = prefs.edit()
        edit.putString(id, customers.id)
        edit.apply()
    }

    fun getUser(): UserData{
        val user = UserData(prefs.getString(id, "")!!,
            prefs.getString(name, "")!!,
            prefs.getString(contact, "")!!,
            prefs.getString(identity, "")!!,
            prefs.getString(address,"")!!,
            prefs.getString(remark,"")!!,
            prefs.getString(status,"")!!)
        return user
    }

    fun getSalesId(): String{
        return prefs.getString(salesId, "0")!!
    }
}