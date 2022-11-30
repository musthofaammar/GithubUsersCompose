package id.eureka.githubuserscompose.users.domain.model.mapper

import id.eureka.githubuserscompose.users.domain.model.UserDomain
import id.eureka.githubuserscompose.core.util.Mapper
import id.eureka.githubuserscompose.users.data.model.UserData

object UserDataToUserDomain : Mapper<UserData, UserDomain> {
    override fun map(input: UserData): UserDomain = UserDomain(
        id = input.id,
        bio = input.bio ?: "",
        createdAt = input.createdAt ?: "",
        login = input.login ?: "",
        updatedAt = input.updatedAt ?: "",
        company = input.company ?: "",
        publicRepos = input.publicRepos ?: 0,
        gravatarId = input.gravatarId ?: "",
        email = input.email ?: "",
        publicGists = input.publicGists ?: 0,
        followers = input.followers ?: 0,
        avatarUrl = input.avatarUrl ?: "",
        following = input.following ?: 0,
        name = input.name ?: ""
    )
}