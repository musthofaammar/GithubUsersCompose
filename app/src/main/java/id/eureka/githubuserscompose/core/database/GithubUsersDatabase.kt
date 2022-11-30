package id.eureka.githubuserscompose.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.eureka.githubuserscompose.users.data.datasource.RepositoryDao
import id.eureka.githubuserscompose.users.data.datasource.UserDao
import id.eureka.githubuserscompose.users.data.model.RepositoryEntity
import id.eureka.githubuserscompose.users.data.model.UserEntity

@Database(entities = [UserEntity::class, RepositoryEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class GithubUsersDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun repositoryDao() : RepositoryDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}