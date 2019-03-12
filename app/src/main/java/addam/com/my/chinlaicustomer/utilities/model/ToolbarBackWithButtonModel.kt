package addam.com.my.chinlaicustomer.utilities.model

data class ToolbarBackWithButtonModel(val title: String, val hasBack: Boolean,val hasButton: Boolean, val btnCallback: () -> Unit = {}, val callback: () -> Unit = {}) {
    fun onBackPressed() {
        if(hasBack) {
            callback.invoke()
        }
    }

    fun onButtonPressed(){
        if (hasButton){
            btnCallback.invoke()
        }
    }
}