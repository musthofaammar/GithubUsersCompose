package id.eureka.githubuserscompose.users.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val bio: String? = null,
    val createdAt: String? = null,
    val login: String? = null,
    val updatedAt: String? = null,
    val company: String? = null,
    val publicRepos: Int? = null,
    val gravatarId: String? = null,
    val email: String? = null,
    val publicGists: Int? = null,
    val followers: Int? = null,
    val avatarUrl: String? = null,
    val following: Int? = null,
    val name: String? = null
)