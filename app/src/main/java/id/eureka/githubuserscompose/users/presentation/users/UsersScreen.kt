package id.eureka.githubuserscompose.users.presentation.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import id.eureka.githubuserscompose.R
import id.eureka.githubuserscompose.ui.theme.GithubUsersComposeTheme
import id.eureka.githubuserscompose.users.presentation.model.User

@Composable
fun UserScreen(
    users: List<User>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            SearchBar(modifier = Modifier.background(MaterialTheme.colors.primary))
        }
        items(users, key = { it.id }) { user ->
            UserItem(photoUrl = user.avatarUrl, username = user.name.ifBlank { user.login })
        }
    }
}

@Composable
fun UserItem(
    photoUrl: String,
    username: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = stringResource(id = R.string.user_image),
                modifier = Modifier
                    .padding(8.dp)
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Text(
                text = username,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        leadingIcon = {
            Icon(contentDescription = null, imageVector = Icons.Default.Search)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = "Cari pengguna"
            )
        },
        onValueChange = {},
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(4.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    GithubUsersComposeTheme {
        SearchBar()
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    GithubUsersComposeTheme {
        UserItem(photoUrl = "", username = "Ahmad Janku")
    }
}