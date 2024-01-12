package com.msarangal.crypto.domain.use_case.get_coins

import com.msarangal.crypto.common.Resource
import com.msarangal.crypto.data.dto.toCoin
import com.msarangal.crypto.domain.model.Coin
import com.msarangal.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    /**
     * A UseCase should only have 1 public function:
     * A function to execute that use case
     */

    // some people name this function execute()
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
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