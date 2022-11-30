package id.eureka.githubuserscompose.core.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.eureka.githubusers.users.domain.usecase.*
import id.eureka.githubuserscompose.users.domain.usecase.GetRepositoriesUseCase
import id.eureka.githubuserscompose.users.domain.usecase.GetRepositoriesUseCaseImpl
import id.eureka.githubuserscompose.users.domain.usecase.GetUserByUserIdOrUserNameUseCase
import id.eureka.githubuserscompose.users.domain.usecase.GetUserByUserIdOrUserNameUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun provideSearchUserUseCase(searchUserUseCaseImpl: SearchUserUseCaseImpl): SearchUserUseCase

    @Binds
    abstract fun provideGetUserByUserIdOrUserNameUseCase(getUserByUserIdOrUserNameUseCaseImpl: GetUserByUserIdOrUserNameUseCaseImpl): GetUserByUserIdOrUserNameUseCase

    @Binds
    abstract fun provideGetRepositoriesUseCase(getRepositoriesUseCaseImpl: GetRepositoriesUseCaseImpl): GetRepositoriesUseCase
}