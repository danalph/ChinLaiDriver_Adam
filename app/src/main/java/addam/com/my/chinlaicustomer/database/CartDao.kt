package addam.com.my.chinlaicustomer.database

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface CartDao{
    @Query("SELECT * FROM cart")
    fun getAll(): Single<List<Cart>>

    @Query("SELECT * FROM cart WHERE customer_id IN (:customerId)")
    fun getCartById(customerId: String): Single<List<Cart>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCart(vararg cart: Cart)

    @Delete
    fun delete(cart: Cart)

    @Query("DELETE FROM cart")
    fun clearTable()

    @Query("DELETE FROM cart WHERE customer_id = (:customerId)")
    fun deleteCartById(customerId: String)

    @Query("UPDATE cart SET product_quantity=:quantity WHERE id=:id")
    fun updateCartQuantityById(quantity: Int, id: Long)
}