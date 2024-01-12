package com.msarangal.crypto.presentation.coin_list

import com.msarangal.crypto.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)