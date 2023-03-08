package io.androidalatan.lifecycle.template.foobar.sub.foo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import io.androidalatan.lifecycle.handler.compose.cache.cached

object FooUi {
    @Composable
    fun Content(fooViewModel: FooViewModel = cached()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val text by fooViewModel.text.observeAsState(initial = "")
            Text(text = text)
        }

    }
}