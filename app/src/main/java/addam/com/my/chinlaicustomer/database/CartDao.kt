package addam.com.my.chinlaicustomer.database

import android.arch.persistence.room.*

@Dao
interface CartDao{
    @Query("SELECT * FROM cart")
    fun getAll(): List<Cart>

    /*@Query("SELECT * FROM cart WHERE product_id IN (:productId)")
    fun loadAllById(productId: Long): List<Cart>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCart(vararg cart: Cart)

    @Delete
    fun delete(cart: Cart)
}