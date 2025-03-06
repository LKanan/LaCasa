import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lacasa.data.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int): User?
}
