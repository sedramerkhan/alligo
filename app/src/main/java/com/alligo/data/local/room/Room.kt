package  com.alligo.data.local.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.alligo.data.local.room.dao.CartDao
import com.alligo.model.CartItem


@Database(
    entities = [CartItem::class],
    version = 1,
    exportSchema = false
)
abstract class Room : RoomDatabase() {
    abstract fun cartItemDao(): CartDao


}