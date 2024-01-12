package com.msarangal.crypto.data.repository

import com.msarangal.crypto.data.dto.CoinDetailDto
import com.msarangal.crypto.data.dto.CoinDto
import com.msarangal.crypto.data.remote.CoinPaprikaApi
import com.msarangal.crypto.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}