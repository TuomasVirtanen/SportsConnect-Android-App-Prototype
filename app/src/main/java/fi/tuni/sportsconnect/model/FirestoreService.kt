package fi.tuni.sportsconnect.model

interface FirestoreService {
    suspend fun createPlayerProfile(playerAccount: PlayerAccount)
    suspend fun readPlayerProfile(playerAccountId: String): PlayerAccount?
    suspend fun updatePlayerProfile(playerAccount: PlayerAccount)
    suspend fun deletePlayerProfile(playerAccountId: String)
}