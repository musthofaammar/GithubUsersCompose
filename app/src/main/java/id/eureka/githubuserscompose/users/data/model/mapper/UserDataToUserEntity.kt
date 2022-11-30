package id.eureka.githubuserscompose.users.data.model.mapper

import id.eureka.githubuserscompose.users.data.model.UserData
import id.eureka.githubuserscompose.users.data.model.UserEntity
import id.eureka.githubuserscompose.core.util.Mapper


object UserDataToUserEntity : Mapper<UserData, UserEntity> {
    override fun map(input: UserData) = UserEntity(
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