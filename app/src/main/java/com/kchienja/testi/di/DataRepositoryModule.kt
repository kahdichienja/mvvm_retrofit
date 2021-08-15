package com.kchienja.testi.di

import com.kchienja.testi.network.ApiService
import com.kchienja.testi.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataRepositoryModule {
    @Provides
    fun provideDataRepository(apiService: ApiService): DataRepository { return DataRepository(apiService) }
}