package fi.tuni.sportsconnect.model

import com.google.firebase.firestore.DocumentId

data class PlayerAccount(
    @DocumentId val id: String = "",
    val bio: String? = null,
    val career: String? = null,
    val cities: MutableList<String>? = null,
    val contactInfo: Map<String, String>? = null,
    val currentTeam: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val leagueLevels: MutableList<String>? = null,
    val positions: MutableList<String>? = null,
    val searchingForTeam: Boolean? = null,
    val strengths: String? = null
)
