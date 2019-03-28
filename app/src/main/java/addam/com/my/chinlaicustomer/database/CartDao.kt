package addam.com.my.chinlaicustomer.database

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface CartDao{
    @Query("SELECT * FROM cart")
    fun getAll(): Single<List<Cart>>

    /*@Query("SELECT * FROM cart WHERE product_id IN (:productId)")
    fun loadAllById(productId: Long): List<Cart>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCart(vararg cart: Cart)

    @Delete
    fun delete(cart: Cart)

    @Query("DELETE FROM cart")
    fun clearTable()
}