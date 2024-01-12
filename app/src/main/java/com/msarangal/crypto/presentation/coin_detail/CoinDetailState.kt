package com.msarangal.crypto.presentation.coin_detail

import com.msarangal.crypto.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)