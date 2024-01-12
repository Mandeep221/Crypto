package com.msarangal.crypto.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msarangal.crypto.common.Resource
import com.msarangal.crypto.domain.model.Coin
import com.msarangal.crypto.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {
    // Since we have UseCases, VMs will now contain less business logic and only focus on maintaining the
    // App state

    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun updateState(state: CoinListState) {
        _state.value = state
    }

    private fun getCoins() {
        getCoinsUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> updateState(
                    CoinListState(
                        coins = result.data ?: emptyList()
                    )
                )

                is Resource.Error -> updateState(
                    CoinListState(
                        error = result.message ?: "An Unexpected error occurred"
                    )
                )

                is Resource.Loading -> updateState(CoinListState(isLoading = true))
            }
        }.launchIn(viewModelScope)
    }
}

