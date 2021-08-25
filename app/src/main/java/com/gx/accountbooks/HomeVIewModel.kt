package com.gx.accountbooks

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVIewModel @Inject constructor(
    var homeR: HomeRepository
) : ViewModel() {
    var name = "HomeVIewModel"

}