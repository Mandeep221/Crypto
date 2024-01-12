package com.msarangal.crypto.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msarangal.crypto.common.Constants.PARAM_COIN_ID
import com.msarangal.crypto.common.Resource
import com.msarangal.crypto.domain.use_case.get_coin.GetCoinDetailsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
@Param [savedStateHandle] kind of a bundle that contains state, if a viewModel needs to be resumed after process death
Cool thing is that it also contains Navigation parameters that are passed inside intents from one screen to another.
 */
class CoinDetailsViewModel @Inject constructor(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // Since we have UseCases, VMs will now contain less business logic and only focus on maintaining the
    // App state

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(PARAM_COIN_ID)?.let { coinId ->
            getCoinDetailsFor(id = coinId)
        }
    }

    private fun updateState(state: CoinDetailState) {
        _state.value = state
    }

    private fun getCoinDetailsFor(id: String) {
        getCoinDetailsUseCase.invoke(id).onEach { result ->
            when (result) {
                is Resource.Success -> updateState(
                    CoinDetailState(
                        coin = result.data
                    )
                )

                is Resource.Error -> updateState(
                    CoinDetailState(
                        error = result.message ?: "An Unexpected error occurred"
                    )
                )

                is Resource.Loading -> updateState(CoinDetailState(isLoading = true))
            }
        }.launchIn(viewModelScope)
    }
}