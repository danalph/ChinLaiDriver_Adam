package addam.com.my.chinlaicustomer.utilities.model

data class ToolbarWithBackModel(val title: String, val hasBack: Boolean, val callback: () -> Unit = {}) {
    fun onBackPressed() {
        if(hasBack) {
            callback.invoke()
        }
    }
}