package id.eureka.githubuserscompose.users.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import id.eureka.githubuserscompose.users.data.model.UserEntity
import id.eureka.githubuserscompose.users.data.model.mapper.UserDataToUserEntity
import id.eureka.githubuserscompose.users.data.model.mapper.UserNetworkDataToUserData
import id.eureka.githubuserscompose.core.api.ApiServices
import id.eureka.githubuserscompose.core.database.RemoteKeyDao
import id.eureka.githubuserscompose.core.database.RemoteKeys
import id.eureka.githubuserscompose.core.util.ErrorMapper

@OptIn(ExperimentalPagingApi::class)
class UsersRemoteMediator(
    private val userDao: UserDao,
    private val remoteKeysDao: RemoteKeyDao,
    private val services: ApiServices,
    private val errorMapper: ErrorMapper,
    private val userName: String,
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, UserEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }

        try {
            val responseData =
                services.searchUsers(userName.ifEmpty { "\"\"" }, page, state.config.pageSize)
//            val endOfPaginationReached = responseData.body()?.items.isNullOrEmpty()

            val endOfPaginationReached =
                responseData.body()?.items.isNullOrEmpty() || (!responseData.body()?.items.isNullOrEmpty() && responseData.body()?.items?.size!! < NETWORK_CALL_SIZE)


            val userEntities = if (!endOfPaginationReached) {
                responseData.body()?.items?.map {
                    UserDataToUserEntity.map(UserNetworkDataToUserData.map(it))
                } ?: emptyList()
            } else {
                emptyList()
            }

            if (loadType == LoadType.REFRESH && responseData.isSuccessful) {
                remoteKeysDao.deleteUserRemoteKeys()
                userDao.deleteUsersExceptFullData()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = responseData.body()?.items?.map {
                RemoteKeys(id = "u${it.id}", prevKey = prevKey, nextKey = nextKey, 1)
            }

            if (!keys.isNullOrEmpty()) {
                remoteKeysDao.insertAll(keys)
            }

            userEntities.forEach { userEntity ->
                try {
                    val user = userDao.getUser(userEntity.id)
                    if (user.updatedAt.isNullOrEmpty()) {
                        userDao.insertUser(userEntity)
                    }
                } catch (exception: Exception) {
                    userDao.insertUser(userEntity)
                }
            }

            if (!responseData.isSuccessful) {
                val message =
                    errorMapper.getErrorMessageFromBody(responseData.errorBody()?.string())
                throw Exception(message)
            }


            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            remoteKeysDao.getUserRemoteKeysId("u${data.id}")
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            remoteKeysDao.getUserRemoteKeysId("u${data.id}")
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getUserRemoteKeysId("u${id}")
            }
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val NETWORK_CALL_SIZE = 25
    }
}