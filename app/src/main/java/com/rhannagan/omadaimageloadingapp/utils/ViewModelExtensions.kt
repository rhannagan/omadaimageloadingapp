package com.rhannagan.omadaimageloadingapp.utils

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> hiltActivityViewModel(): VM {
    val activity = LocalActivity.current as? ComponentActivity
        ?: throw IllegalStateException(
            "hiltActivityViewModel must be called from a ComponentActivity!!!"
        )

    return viewModel<VM>(viewModelStoreOwner = activity)
}