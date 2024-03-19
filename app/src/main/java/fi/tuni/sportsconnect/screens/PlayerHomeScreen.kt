package fi.tuni.sportsconnect.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.R
import fi.tuni.sportsconnect.model.Post
import fi.tuni.sportsconnect.ui.theme.Dark
import fi.tuni.sportsconnect.ui.theme.White
import fi.tuni.sportsconnect.viewModels.PlayerHomeViewModel
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun PlayerHomeScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: PlayerHomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold {
        val posts by viewModel.posts.collectAsState(emptyList())

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 12.dp, 0.dp, 0.dp)) {
            LazyColumn {
                items(posts, key = { it.id }) {postItem ->
                    PostItem(
                        post = postItem,
                        onActionClick = { viewModel.onProfileClick(openScreen, postItem.club["clubId"]!!)})
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp, 0.dp, 5.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_team_logo),
                    contentDescription = "noLogo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onActionClick(post.club["clubId"]!!) }
                )
                Text(
                    text = post.club["clubName"].orEmpty(),
                    modifier = Modifier
                        .padding(5.dp, 12.dp, 5.dp, 12.dp)
                        .clickable { onActionClick(post.club["clubId"]!!) },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "·",
                    modifier = Modifier
                        .padding(5.dp),
                    style = TextStyle(
                        fontSize = 25.sp
                    ),
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(post.created.toDate().toInstant().atZone(
                        ZoneOffset.UTC).toLocalDate()),
                    modifier = Modifier.padding(5.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.W200
                )
            }
            FlowRow(
                modifier = Modifier
                    .padding(5.dp, 0.dp, 5.dp, 10.dp),

            ) {
                    Text(
                        text = post.club["city"].orEmpty(),
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(White)
                            .border(
                                border = BorderStroke(2.dp, color = Dark),
                                shape = CircleShape,
                                )
                            .padding(5.dp)

                    )
                    Text(
                        text = post.club["level"].orEmpty(),
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(White)
                            .border(
                                border = BorderStroke(2.dp, color = Dark),
                                shape = CircleShape)
                            .padding(5.dp)
                    )
                    post.positions.forEach{ post ->
                        Text(
                            text = post,
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(CircleShape)
                                .background(White)
                                .border(
                                    border = BorderStroke(2.dp, color = Dark),
                                    shape = CircleShape)
                                .padding(5.dp)
                        )
                    }
            }
            Text(
                text = post.text,
                modifier = Modifier.padding(10.dp, 0.dp, 12.dp, 5.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}