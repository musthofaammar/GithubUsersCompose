package id.eureka.githubuserscompose.users.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import id.eureka.githubuserscompose.users.domain.model.UserDomain
import id.eureka.githubuserscompose.core.model.Result

interface UsersRepository {
    suspend fun searchUsers(userName : String = "") : Flow<PagingData<UserDomain>>
    suspend fun getUserDetail(userName: String, userId : Int) : Flow<Result<UserDomain>>
}