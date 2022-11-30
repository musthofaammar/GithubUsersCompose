package id.eureka.githubuserscompose.core.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.eureka.githubuserscompose.core.provider.DispatcherProvider
import id.eureka.githubuserscompose.core.provider.DispatcherProviderImp
import id.eureka.githubuserscompose.core.provider.ResourceProvider
import id.eureka.githubuserscompose.core.provider.ResourceProviderImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    abstract fun provideResourceProvider(resourceProviderImpl: ResourceProviderImpl): ResourceProvider

    @Binds
    abstract fun provideDispatcherProvider(dispatcherProviderImp: DispatcherProviderImp): DispatcherProvider
}