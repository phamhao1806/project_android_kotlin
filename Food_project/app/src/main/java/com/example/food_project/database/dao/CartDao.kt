// CartDao.kt
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.food_project.model.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items WHERE userId = :uid")
    fun getAllItems(uid: String): LiveData<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Update
    suspend fun update(item: CartItem)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items WHERE userId = :uid")
    suspend fun clearCart(uid: String)
}
