package id.eureka.githubuserscompose.users.data.datasource

import androidx.paging.*
import id.eureka.githubuserscompose.users.data.model.mapper.UserDetailNetworkDataToUserData
import id.eureka.githubuserscompose.users.data.model.mapper.UserEntityToUserData
import id.eureka.githubuserscompose.users.domain.model.UserDomain
import id.eureka.githubuserscompose.users.domain.model.mapper.UserDataToUserDomain
import id.eureka.githubuserscompose.users.domain.repository.UsersRepository
import id.eureka.githubuserscompose.R
import id.eureka.githubuserscompose.core.api.ApiServices
import id.eureka.githubuserscompose.core.database.RemoteKeyDao
import id.eureka.githubuserscompose.core.model.Result
import id.eureka.githubuserscompose.core.provider.DispatcherProvider
import id.eureka.githubuserscompose.core.provider.ResourceProvider
import id.eureka.githubuserscompose.core.util.ErrorMapper
import id.eureka.githubuserscompose.users.data.model.mapper.UserDataToUserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class UsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val services: ApiServices,
    private val resourceProvider: ResourceProvider,
    private val errorMapper: ErrorMapper,
    private val dispatcherProvider: DispatcherProvider,
) : UsersRepository {
    override suspend fun searchUsers(userName: String): Flow<PagingData<UserDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = UsersRemoteMediator.NETWORK_CALL_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = UsersRemoteMediator(
                userDao,
                remoteKeyDao,
                services,
                errorMapper,
                userName
            ),
            pagingSourceFactory = {
                if (userName.isEmpty()) userDao.getUsers() else userDao.getUsers(userName)
            }
        ).flow.flowOn(dispatcherProvider.getIO()).map { paging ->
            paging.map { userEntity ->
                UserDataToUserDomain.map(UserEntityToUserData.map(userEntity))
            }
        }
    }

    override suspend fun getUserDetail(userName: String, userId: Int): Flow<Result<UserDomain>> =
        flow {
            try {
                val response = services.getUserByUsername(userName)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        userDao.insertUser(
                            UserDataToUserEntity.map(
                                UserDetailNetworkDataToUserData.map(
                                    response.body()!!
                                )
                            )
                        )
                    }
                }

                val user = userDao.getUser(userId)
                val userData = UserEntityToUserData.map(user)
                emit(Result.Success(userData))

            } catch (e: UnknownHostException) {
                val user = userDao.getUser(userId)
                val userData = UserEntityToUserData.map(user)
                emit(Result.Success(userData))
            } catch (e: Exception) {
                emit(
                    Result.Error(
                        e.hashCode(),
                        e.localizedMessage ?: resourceProvider.getString(R.string.something_wrong)
                    )
                )
            }
        }.flowOn(dispatcherProvider.getIO()).map { result ->
            when (result) {
                is Result.Error -> result
                is Result.Success -> Result.Success(UserDataToUserDomain.map(result.data))
            }
        }
}