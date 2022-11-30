package id.eureka.githubuserscompose.core.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.eureka.githubuserscompose.users.data.datasource.RepositoriesRepositoryImpl
import id.eureka.githubuserscompose.users.data.datasource.UsersRepositoryImpl
import id.eureka.githubuserscompose.users.domain.repository.RepositoriesRepository
import id.eureka.githubuserscompose.users.domain.repository.UsersRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUsersRepository(userRepositoryImpl: UsersRepositoryImpl): UsersRepository

    @Binds
    abstract fun provideRepositoriesRepository(repositoryImpl: RepositoriesRepositoryImpl): RepositoriesRepository
}