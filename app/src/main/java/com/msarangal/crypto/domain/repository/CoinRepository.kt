package com.msarangal.crypto.domain.repository

import com.msarangal.crypto.data.dto.CoinDetailDto
import com.msarangal.crypto.data.dto.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}