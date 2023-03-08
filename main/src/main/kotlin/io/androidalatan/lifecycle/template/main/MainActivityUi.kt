package io.androidalatan.lifecycle.template.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.androidalatan.component.view.compose.api.view.onClick
import io.androidalatan.lifecycle.handler.compose.activity.localowners.LocalComposeEventTriggerOwner
import io.androidalatan.lifecycle.handler.compose.cache.cached

object MainActivityUi {
    @Composable
    fun Content(viewModel: MainActivityViewModel = cached()) {
        val resumeText by viewModel.resumeText.observeAsState("")
        val createText by viewModel.createText.observeAsState("")
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = resumeText)
            Text(text = createText)
            val interactionTrigger = LocalComposeEventTriggerOwner.current
            Button(onClick = interactionTrigger.onClick(MainActivityViewModel.clickId)) {
                Text(text = "FooBar Screen")
            }
        }

    }
}