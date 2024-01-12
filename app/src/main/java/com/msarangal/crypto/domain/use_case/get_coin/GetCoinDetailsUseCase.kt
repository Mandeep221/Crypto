package com.msarangal.crypto.domain.use_case.get_coin

import com.msarangal.crypto.common.Resource
import com.msarangal.crypto.data.dto.toCoinDetail
import com.msarangal.crypto.domain.model.CoinDetail
import com.msarangal.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    // some people name this function execute()
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            // Happens when api call is not successful: NOT 2xx response
            emit(Resource.Error(e.localizedMessage ?: "An Expected error occurred"))
        } catch (e: IOException) {
            // happens when server can't be reached, request times out: No internet, Or server is offline
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Couldn't reach server, Check your internet connection"
                )
            )
        }
    }
}
