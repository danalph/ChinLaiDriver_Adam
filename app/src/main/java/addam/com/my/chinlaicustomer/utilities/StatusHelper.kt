package addam.com.my.chinlaicustomer.utilities

import addam.com.my.chinlaicustomer.R

class StatusHelper{
    companion object {
        fun getStatus(status: String): String{
            return when(status){
                "0" -> "Incomplete"
                "1" -> "Completed"
                "2" -> "Pending"
                "3" -> "Confirmed"
                "4" -> "Delivery"
                else -> "Incomplete"
            }
        }

        fun getStatusColor(status: String): Int{
            return when(status){
                "0" -> R.color.colorRed
                "1" -> R.color.colorGreen
                "2" -> R.color.colorYellow
                "3" -> R.color.colorBlue
                "4" -> R.color.colorOrange
                else -> R.color.colorRed
            }
        }
    }
}