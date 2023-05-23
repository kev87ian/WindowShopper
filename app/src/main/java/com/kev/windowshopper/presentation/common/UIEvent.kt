package com.kev.windowshopper.presentation.common

sealed class UIEvent {
    data class ShowSnackbar(val message: String) : UIEvent()
}
