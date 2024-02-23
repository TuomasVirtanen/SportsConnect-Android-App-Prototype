package fi.tuni.sportsconnect.model

import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<PlayerAccount?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()
}