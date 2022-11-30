package id.eureka.githubuserscompose.users.data.model.mapper

import id.eureka.githubuserscompose.core.util.Mapper
import id.eureka.githubuserscompose.users.data.model.UserData
import id.eureka.githubuserscompose.users.data.model.UserNetworkData

object UserNetworkDataToUserData : Mapper<UserNetworkData, UserData> {
    override fun map(input: UserNetworkData) = UserData(
        id = input.id ?: 0,
        bio = null,
        createdAt = null,
        login = input.login,
        updatedAt = null,
        company = null,
        publicRepos = null,
        gravatarId = input.gravatarId,
        email = null,
        publicGists = null,
        followers = null,
        avatarUrl = input.avatarUrl,
        following = null,
        name = null
    )
}