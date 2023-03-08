package io.androidalatan.lifecycle.template.valueholder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <ValueType> ValueHolder<ValueType>.observeAsState(): State<ValueType> {
    val value = get()
    val state = remember { mutableStateOf(value) }

    DisposableEffect(state) {
        val callback = ValueHolder.Callback<ValueType> { state.value = it }
        registerObserver(callback)
        onDispose {
            unregisterObserver(callback)
        }
    }
    return state
}