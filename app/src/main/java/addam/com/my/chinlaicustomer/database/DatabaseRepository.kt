package addam.com.my.chinlaicustomer.database

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Addam on 7/1/2019.
 */
@Singleton
class DatabaseRepository @Inject constructor(private val userDao: UserDao, private val cartDao: CartDao){
    fun getUser(): Single<User> = userDao.getUser()

    fun insertUser(user: User) = userDao.insert(user)

    fun getCart():Single<List<Cart>> = cartDao.getAll()

    fun addToCart(cart: Cart) = cartDao.addToCart(cart)

    fun deleteCart(cart: Cart) = cartDao.delete(cart)

    fun clearTable() = cartDao.clearTable()

    fun deleteCartById(customerId: String) = cartDao.deleteCartById(customerId)

    fun getCartById(customerId: String):Single<List<Cart>> = cartDao.getCartById(customerId)

    fun updateCartQuantityById(quantity: Int, id: Long) = cartDao.updateCartQuantityById(quantity, id)
}