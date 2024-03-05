package fi.tuni.sportsconnect.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.model.Post
import fi.tuni.sportsconnect.viewModels.PlayerHomeViewModel

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun PlayerHomeScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerHomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold {
        val posts by viewModel.posts.collectAsState(emptyList())

        Column(modifier = Modifier
            .fillMaxSize()) {
            LazyColumn {
                items(posts, key = { it.id }) {postItem ->
                    PostItem(
                        post = postItem,
                        onActionClick = {})
                }
            }
        }
    }
}


@Composable
fun PostItem(
    post: Post,
    onActionClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onActionClick(post.id) }
        ) {
            Text(
                text = post.header,
                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = post.text,
                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = post.created.toDate().toString(),
                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}