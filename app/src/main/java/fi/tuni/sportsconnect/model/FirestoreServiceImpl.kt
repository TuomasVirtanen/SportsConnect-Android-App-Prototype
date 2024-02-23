package fi.tuni.sportsconnect.model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor(private val auth: AccountService): FirestoreService {

    override suspend fun createPlayerProfile(newPlayerAccount: PlayerAccount) {
        Firebase.firestore
            .collection(PLAYER_ACCOUNT_COLLECTION)
            .document(newPlayerAccount.id).set(newPlayerAccount).await()
    }

    override suspend fun readPlayerProfile(playerAccountId: String): PlayerAccount? {
        return Firebase.firestore
            .collection(PLAYER_ACCOUNT_COLLECTION)
            .document(playerAccountId).get().await().toObject()
    }

    override suspend fun updatePlayerProfile(playerAccount: PlayerAccount) {
        Firebase.firestore
            .collection(PLAYER_ACCOUNT_COLLECTION)
            .document(playerAccount.id).set(playerAccount).await()
    }

    override suspend fun deletePlayerProfile(playerAccountId: String) {
        Firebase.firestore
            .collection(PLAYER_ACCOUNT_COLLECTION)
            .document(playerAccountId).delete().await()
    }

    override suspend fun createClubProfile(newClubAccount: ClubAccount) {
        Firebase.firestore
            .collection(CLUB_ACCOUNT_COLLECTION)
            .document(newClubAccount.id).set(newClubAccount).await()
    }

    override suspend fun readClubProfile(clubAccountId: String): ClubAccount? {
        return Firebase.firestore
            .collection(CLUB_ACCOUNT_COLLECTION)
            .document(clubAccountId).get().await().toObject()
    }

    override suspend fun updateClubProfile(clubAccount: ClubAccount) {
        Firebase.firestore
            .collection(CLUB_ACCOUNT_COLLECTION)
            .document(clubAccount.id).set(clubAccount).await()
    }

    override suspend fun deleteClubProfile(clubAccountId: String) {
        Firebase.firestore
            .collection(CLUB_ACCOUNT_COLLECTION)
            .document(clubAccountId).delete().await()
    }

    companion object {
        private const val PLAYER_ACCOUNT_COLLECTION = "playerAccounts"
        private const val CLUB_ACCOUNT_COLLECTION = "clubAccounts"
    }
}
