package fi.tuni.sportsconnect.model

interface FirestoreService {
    suspend fun createPlayerProfile(playerAccount: PlayerAccount)
    suspend fun readPlayerProfile(playerAccountId: String): PlayerAccount?
    suspend fun updatePlayerProfile(playerAccount: PlayerAccount)
    suspend fun deletePlayerProfile(playerAccountId: String)
    suspend fun createClubProfile(clubAccount: ClubAccount)
    suspend fun readClubProfile(clubAccountId: String): ClubAccount?
    suspend fun updateClubProfile(clubAccount: ClubAccount)
    suspend fun deleteClubProfile(clubAccountId: String)
}