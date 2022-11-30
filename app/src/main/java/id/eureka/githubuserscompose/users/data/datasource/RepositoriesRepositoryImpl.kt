package id.eureka.githubuserscompose.users.data.datasource

import androidx.paging.*
import id.eureka.githubuserscompose.users.data.model.mapper.RepositoryEntityToRepositoryData
import id.eureka.githubuserscompose.users.domain.model.RepositoryDomain
import id.eureka.githubuserscompose.users.domain.model.mapper.RepositoryDataToRepositoryDomain
import id.eureka.githubuserscompose.users.domain.repository.RepositoriesRepository
import id.eureka.githubuserscompose.core.api.ApiServices
import id.eureka.githubuserscompose.core.database.RemoteKeyDao
import id.eureka.githubuserscompose.core.provider.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class RepositoriesRepositoryImpl @Inject constructor(
    private val repositoryDao: RepositoryDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val services: ApiServices,
    private val dispatcherProvider: DispatcherProvider,
) : RepositoriesRepository {
    override suspend fun getRepositories(
        userName: String,
        userId: Int,
    ): Flow<PagingData<RepositoryDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = RepositoriesRemoteMediator.NETWORK_CALL_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = RepositoriesRemoteMediator(
                repositoryDao,
                remoteKeyDao,
                services,
                userName,
                userId
            ),
            pagingSourceFactory = {
                repositoryDao.getRepositoriesByUserId(userId)
            }
        ).flow.flowOn(dispatcherProvider.getIO()).mapLatest { paging ->
            paging.map { userEntity ->
                RepositoryDataToRepositoryDomain.map(RepositoryEntityToRepositoryData.map(userEntity))
            }
        }
    }
}