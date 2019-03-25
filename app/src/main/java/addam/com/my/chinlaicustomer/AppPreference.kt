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
        const val personName = "person"
        const val contact = "contact"
        const val address1 = "address1"
        const val address2 = "address2"
        const val address3 = "address3"
        const val stateName = "state"
        const val areaName = "areaName"
        const val postcode = "postcode"
        const val password = "password"
        const val salesId = "sales"
        const val address = "address"
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
        edit.putString(personName, data.person_name)
        edit.putString(contact, data.person_contact)
        edit.putString(address1, data.address1)
        edit.putString(address2, data.address2)
        edit.putString(address3, data.address3)
        edit.putString(stateName, data.stateName)
        edit.putString(areaName, data.areaName)
        edit.putString(postcode, data.postcode)
        edit.putString(password, data.password)
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
        edit.putString(personName, "")
        edit.putString(contact, "")
        edit.putString(address,"")
        edit.putString(stateName, "")
        edit.putString(postcode, "")
        edit.putString(password, "")
        edit.apply()
    }

    fun setCustomerId(customers: Customers){
        val edit = prefs.edit()
        edit.putString(id, customers.id)
        edit.apply()
    }

    fun getUser(): UserData{
        val user = UserData(
        prefs.getString(id, "")!!,
            prefs.getString(name, "")!!,
            prefs.getString(personName, "")!!,
            prefs.getString(contact, "")!!,
            prefs.getString(address1,"")!!,
            prefs.getString(address2,"")!!,
            prefs.getString(address3,"")!!,
            prefs.getString(stateName, "")!!,
            prefs.getString(areaName, "")!!,
            prefs.getString(postcode, "")!!,
            prefs.getString(password, "")!!
        )
        return user
    }

    fun getSalesId(): String{
        return prefs.getString(salesId, "0")!!
    }
}