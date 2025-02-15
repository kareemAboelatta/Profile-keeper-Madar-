package com.example.profilekeeper.di

import com.example.profilekeeper.data.local.UserDao
import com.example.profilekeeper.domain.repository.RegisterRepository
import com.example.profilekeeper.data.repository.RegisterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRegisterRepository(
        userDao: UserDao
    ): RegisterRepository {
        return RegisterRepositoryImpl(userDao)
    }
}
