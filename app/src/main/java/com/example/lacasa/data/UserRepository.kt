package com.example.lacasa.data
import com.example.lacasa.model.UserDao
import kotlinx.coroutines.flow.Flow
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate

class UserRepository(private val userDao: UserDao) {
    val users: Flow<List<User>> = userDao.getAllUsers()

    suspend fun addUser(lastName: String, name: String, email: String, password: String, signupDate: LocalDate) {
        val user = User(
            lastName = lastName,
            name = name,
            email = email,
            password = password,
            signupDate = signupDate
        )
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun authenticateUser(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return if (user != null) {
            // Vérifier le mot de passe
            checkPassword(password, user.password)
        } else {
            false // Aucun utilisateur trouvé
        }
    }


    fun checkPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }
}