package id.eureka.githubuserscompose.users.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import id.eureka.githubuserscompose.users.presentation.model.Repository
import id.eureka.githubuserscompose.users.presentation.model.mapper.RepositoryDomainToRepository
import id.eureka.githubuserscompose.users.domain.repository.RepositoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetRepositoriesUseCase {
    suspend fun getRepositories(userName: String, userId: Int): Flow<PagingData<Repository>>
}

class GetRepositoriesUseCaseImpl @Inject constructor(
    private val repository: RepositoriesRepository,
) : GetRepositoriesUseCase {
    override suspend fun getRepositories(
        userName: String,
        userId: Int,
    ): Flow<PagingData<Repository>> =
        repository.getRepositories(userName, userId).map { paging ->
            paging.map { repositoryDomain ->
                RepositoryDomainToRepository.map(repositoryDomain)
            }
        }
}