package id.eureka.githubuserscompose.users.data.model.mapper

import id.eureka.githubuserscompose.users.data.model.RepositoryData
import id.eureka.githubuserscompose.users.data.model.RepositoryEntity
import id.eureka.githubuserscompose.core.util.Mapper

object RepositoryDataToRepositoryEntity : Mapper<RepositoryData, RepositoryEntity> {
    override fun map(input: RepositoryData): RepositoryEntity = RepositoryEntity(
        id = input.id,
        userId = input.userId,
        allowForking = input.allowForking,
        stargazersCount = input.stargazersCount,
        isTemplate = input.isTemplate,
        pushedAt = input.pushedAt,
        language = input.language,
        hasDiscussions = input.hasDiscussions,
        forks = input.forks,
        visibility = input.visibility,
        fullName = input.fullName,
        size = input.size,
        name = input.name,
        defaultBranch = input.defaultBranch,
        jsonMemberPrivate = input.jsonMemberPrivate,
        hasDownloads = input.hasDownloads,
        openIssuesCount = input.openIssuesCount,
        description = input.description,
        createdAt = input.createdAt,
        watchers = input.watchers,
        hasProjects = input.hasProjects,
        archived = input.archived,
        hasWiki = input.hasWiki,
        updatedAt = input.updatedAt,
        disabled = input.disabled,
        hasIssues = input.hasIssues,
        hasPages = input.hasPages,
        webCommitSignoffRequired = input.webCommitSignoffRequired,
        fork = input.fork,
        openIssues = input.openIssues,
        watchersCount = input.watchersCount,
        nodeId = input.nodeId,
        homepage = input.homepage,
        forksCount = input.forksCount
    )
}