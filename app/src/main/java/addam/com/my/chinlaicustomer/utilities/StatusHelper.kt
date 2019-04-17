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
                "pending" -> R.color.grey
                "confirmed" -> R.color.colorYellow
                "processing" -> R.color.colorLightRed
                "delivering" -> R.color.colorLightBlue
                "completed" -> R.color.colorBlue
                "unknown" -> R.color.colorGreen
                else -> R.color.colorRed
            }
        }
    }
}