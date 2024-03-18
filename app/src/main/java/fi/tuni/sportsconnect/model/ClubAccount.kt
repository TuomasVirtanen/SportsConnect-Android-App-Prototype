package fi.tuni.sportsconnect.model

import com.google.firebase.firestore.DocumentId

data class ClubAccount(
    @DocumentId val id: String = "",
    val clubName: String = "",
    val city: String = "",
    val bio: String? = null,
    val contactInfo: Map<String, String>? = null,
    val leagueLevel: String = "",
    val trainingPlaceAndTime: String? = null,
)