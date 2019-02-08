package addam.com.my.chinlaicustomer.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


/**
 * Created by owner on 2/5/2018.
 */
class BaseConverter {
    companion object {

        fun convertToBase64(imagePath: String): String {

            val bm = BitmapFactory.decodeFile(imagePath)
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 50, baos)
            val byteArrayImage = baos.toByteArray()
            val encodeString = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
            return "data:image/png;base64,$encodeString"


        }

        fun resizeImageAndConvert(imagePath: String, scaleTo: Int = 1024):String
        {
            val bm = BitmapFactory.decodeFile(imagePath)
            val bmOptions = BitmapFactory.Options()
            bmOptions.inJustDecodeBounds = true
            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight // Determine how much to scale down the image
            val scaleFactor = Math.min(photoW / scaleTo, photoH / scaleTo)
            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor
            val resized = BitmapFactory.decodeFile(imagePath, bmOptions)

            val baos = ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.PNG, 50, baos)
            val byteArrayImage = baos.toByteArray()
            val encodeString = Base64.encodeToString(byteArrayImage, Base64.NO_WRAP)
            return "data:image/png;base64,$encodeString"

        }
    }


}