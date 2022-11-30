package id.eureka.githubuserscompose.users.data.model.mapper

import id.eureka.githubuserscompose.users.data.model.UserData
import id.eureka.githubuserscompose.users.data.model.UserDetailNetworkData
import id.eureka.githubuserscompose.core.util.Mapper

object UserDetailNetworkDataToUserData : Mapper<UserDetailNetworkData, UserData> {
    override fun map(input: UserDetailNetworkData) = UserData(
        id = input.id ?: 0,
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