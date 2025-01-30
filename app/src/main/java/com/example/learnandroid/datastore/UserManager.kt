package com.example.learnandroid.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.learnandroid.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_DATASTORE_FILE_NAME = "user_prefs.pb"

val Context.userDataStore: DataStore<User> by dataStore(
    fileName = USER_DATASTORE_FILE_NAME,
    serializer = UserSerializer
)

@Singleton
class UserManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore: DataStore<User> = context.userDataStore

    fun getUser(): Flow<User> {
        return dataStore.data
    }

    suspend fun updateUser(firstName: String, lastName: String, email: String) {
        dataStore.updateData {
            it.toBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .build()
        }
    }
}
