package addam.com.my.chinlaicustomer.core.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.github.ajalt.timberkt.Timber

class PermissionHelper(val activity: Activity, val callback: PermissionSuccessCallback) {

    private val listPermissionsNeeded = arrayListOf<String>()
    private var requestCode = 0

    companion object {
        var MAPS_PERMISSION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        var PICTURE_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        var PHONE_CALL_PERMISSIONS = arrayOf(Manifest.permission.CALL_PHONE)
        var GALLERY_PERMISSION = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private var PERMISSION_ALL = 1
    }

    fun checkPermission(permissions: Array<String>): Boolean {
        var result: Int
        listPermissionsNeeded.clear()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(activity, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            return false
        }
        return true
    }

    fun requestPermission(requestCode: Int) {
        this.requestCode = requestCode
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toTypedArray(), requestCode)
        }
    }

    fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permissions granted.
            callback.runOperation(requestCode)
        } else {
            var permission = ""
            for (per in permissions) {
                permission += "\n" + per
            }

            showExitDialog()
            Timber.e { "Permission not granted" }
        }
    }

    private fun showExitDialog() {
        if (activity.isFinishing) {
            return
        }

        AlertDialog.Builder(activity)
                .setTitle("Error!")
                .setMessage("Please provide permission")
                .setPositiveButton("OK") { _, _ ->
                    activity.finish()
                }
                .setOnCancelListener { activity.finish() }
                .show()
    }

    interface PermissionSuccessCallback {
        fun runOperation(requestCode: Int)
    }
}