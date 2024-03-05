package fi.tuni.sportsconnect.model

import kotlinx.coroutines.flow.Flow

interface FirestoreService {
    val players: Flow<List<PlayerAccount>>
    val posts: Flow<List<Post>>
    suspend fun isUserPlayer(): Boolean
    suspend fun createPlayerProfile(playerAccount: PlayerAccount)
    suspend fun readPlayerProfile(playerAccountId: String): PlayerAccount?
    suspend fun updatePlayerProfile(playerAccount: PlayerAccount)
    suspend fun deletePlayerProfile(playerAccountId: String)
    suspend fun createClubProfile(clubAccount: ClubAccount)
    suspend fun readClubProfile(clubAccountId: String): ClubAccount?
    suspend fun updateClubProfile(clubAccount: ClubAccount)
    suspend fun deleteClubProfile(clubAccountId: String)
    suspend fun createPost(post: Post)
    suspend fun readPost(postId: String): Post?
    suspend fun updatePost(post: Post)
    suspend fun deletePost(postId: String)
}