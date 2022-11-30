package id.eureka.githubuserscompose.users.presentation.detailuser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import id.eureka.githubuserscompose.ui.theme.GithubUsersComposeTheme
import id.eureka.githubuserscompose.users.presentation.model.User

@Composable
fun DetailUserScreen(
    user: User,
    viewModel: DetailUserViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold { innerPadding ->
        LazyColumn(modifier = modifier.padding(innerPadding)) {
            item {
                DetailUser(
                    userImage = user.avatarUrl,
                    userName = user.login,
                    userRealName = user.name,
                    userBio = user.bio
                )
            }
//            items()
        }
    }
}

@Composable
fun DetailUser(
    userImage: String,
    userName: String,
    userRealName: String,
    userBio: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        AsyncImage(
            model = userImage,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = userRealName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "@${userName}",
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = userBio, color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
fun RepositoryItem(
    repositoryName: String,
    repositoryDescription: String,
    starCount: Int,
    lastUpdated: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = repositoryName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = repositoryDescription,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(14.dp)
            )
            Text(
                text = "$starCount",
                modifier = Modifier.padding(end = 16.dp),
                fontSize = 10.sp,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = lastUpdated,
                fontSize = 10.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailUserPreview() {
    GithubUsersComposeTheme {
        DetailUser(
            userImage = "",
            userName = "musthofaammar",
            userRealName = "Ammar Musthofa",
            userBio = "Are you alive or just existing?"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryItemPreview() {
    GithubUsersComposeTheme {
        RepositoryItem(
            repositoryName = "Android-Clean-Architecture",
            repositoryDescription = "This is a sample app that is part of a series of blog posts I have written about how to architect an android application using Uncle Bob's clean architecture approach.",
            starCount = 55,
            lastUpdated = "Updated 15 hours ago",
            Modifier.fillMaxWidth()
        )
    }
}