package fi.tuni.sportsconnect.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Post(
    @DocumentId val id: String = "",
    val text: String = "",
    val positions: List<String> = mutableListOf(),
    val club: Map<String, String> = mutableMapOf(),
    val created: Timestamp = Timestamp(Date()),
)
