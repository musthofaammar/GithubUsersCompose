package id.eureka.githubuserscompose.users.presentation.model.mapper

import id.eureka.githubuserscompose.users.presentation.model.User
import id.eureka.githubuserscompose.core.util.Mapper
import id.eureka.githubuserscompose.users.domain.model.UserDomain

object UserDomainToUser : Mapper<UserDomain, User> {
    override fun map(input: UserDomain): User = User(
        id = input.id,
        bio = input.bio,
        createdAt = input.createdAt,
        login = input.login,
        updatedAt = input.updatedAt,
        company = input.company,
        publicRepos = input.publicRepos,
        gravatarId = input.gravatarId,
        email = input.email,
        publicGists = input.publicGists,
        followers = input.followers,
        avatarUrl = input.avatarUrl,
        following = input.following,
        name = input.name
    )
}