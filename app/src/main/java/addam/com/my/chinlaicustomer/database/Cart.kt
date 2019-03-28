package addam.com.my.chinlaicustomer.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.drawable.Drawable

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "product_id")
    var productId: Long,
    @ColumnInfo(name = "product_name")
    var productName: String,
    @ColumnInfo(name = "product_price")
    var productPrice: String,
    @ColumnInfo(name = "product_quantity")
    var productQuantity: Int,
    @ColumnInfo(name = "product_image_path")
    val productImagePath:  String,
    var isChecked: Boolean = true
)