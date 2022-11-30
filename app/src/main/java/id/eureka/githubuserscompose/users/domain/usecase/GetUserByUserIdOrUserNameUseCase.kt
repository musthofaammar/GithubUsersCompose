package id.eureka.githubuserscompose.users.domain.usecase

import id.eureka.githubuserscompose.core.model.Result
import id.eureka.githubuserscompose.users.domain.repository.UsersRepository
import id.eureka.githubuserscompose.users.presentation.model.User
import id.eureka.githubuserscompose.users.presentation.model.mapper.UserDomainToUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetUserByUserIdOrUserNameUseCase {
    suspend fun getUserByUserIdOrUserName(userName: String, userId: Int): Flow<Result<User>>
}

class GetUserByUserIdOrUserNameUseCaseImpl @Inject constructor(private val repository: UsersRepository) :
    GetUserByUserIdOrUserNameUseCase {
    override suspend fun getUserByUserIdOrUserName(
        userName: String,
        userId: Int
    ): Flow<Result<User>> = repository.getUserDetail(userName, userId).map { result ->
        when (result) {
            is Result.Success -> Result.Success(UserDomainToUser.map(result.data))
            is Result.Error -> result
        }
    }

}