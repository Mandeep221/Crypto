package com.msarangal.crypto.di

import com.msarangal.crypto.common.Constants.BASE_URL
import com.msarangal.crypto.data.remote.CoinPaprikaApi
import com.msarangal.crypto.data.repository.CoinRepositoryImpl
import com.msarangal.crypto.domain.repository.CoinRepository
import com.msarangal.crypto.domain.use_case.get_coin.GetCoinDetailsUseCase
import com.msarangal.crypto.domain.use_case.get_coins.GetCoinsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesCoinRepository(api: CoinPaprikaApi): CoinRepository {
        return CoinRepositoryImpl(api = api)
    }

    @Singleton
    @Provides
    fun providesCoinApi(): CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Singleton
    @Provides
    fun providesGetCoinsUseCase(repository: CoinRepository): GetCoinsUseCase {
        return GetCoinsUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun providesGetCoinDetailsUseCase(repository: CoinRepository): GetCoinDetailsUseCase {
        return GetCoinDetailsUseCase(repository = repository)
    }
}