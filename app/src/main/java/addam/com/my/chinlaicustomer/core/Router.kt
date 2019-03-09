package addam.com.my.chinlaicustomer.core

import addam.com.my.chinlaicustomer.feature.delivery.DashboardActivity
import addam.com.my.chinlaicustomer.feature.delivery.DeliveryDetailsActivity
import addam.com.my.chinlaicustomer.feature.destination.DestinationActivity
import addam.com.my.chinlaicustomer.feature.login.LoginActivity
import addam.com.my.chinlaicustomer.feature.map.MapActivity
import addam.com.my.chinlaicustomer.feature.password.ResetPasswordActivity
import addam.com.my.chinlaicustomer.feature.profile.ProfileActivity

/**
 * Created by Addam on 7/1/2019.
 */
class Router {
    enum class Destination {
        LOGIN,
        PROFILE,
        DASHBOARD,
        DELIVERY_DETAIL,
        MAP,
        DESTINATION,

        RESET_PASSWORD
    }

    enum class Parameter{
        USERNAME,
        PASSWORD,
        LATITUDE,
        LONGITUDE,
        COMPANY,
        DOC_NUM,
        ADDRESS,
        STATUS,
        TYPE,
        TRIP_ID,
        DOC_ID
    }

    companion object {
        fun getClass(destination: Destination): Class<*> {
            return when (destination) {
                Destination.LOGIN -> LoginActivity::class.java
                Destination.PROFILE -> ProfileActivity::class.java
                Destination.RESET_PASSWORD -> ResetPasswordActivity::class.java
                Destination.DASHBOARD -> DashboardActivity::class.java
                Destination.MAP -> MapActivity::class.java
                Destination.DELIVERY_DETAIL -> DeliveryDetailsActivity::class.java
                Destination.DESTINATION -> DestinationActivity::class.java
                else -> {
                    TODO("Implement Default case")
                }
            }
        }
    }
}