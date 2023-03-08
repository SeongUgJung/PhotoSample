package io.androidalatan.lifecycle.template.foobar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import io.androidalatan.component.view.compose.api.view.onClick
import io.androidalatan.lifecycle.handler.compose.activity.localowners.LocalComposeEventTriggerOwner
import io.androidalatan.lifecycle.handler.compose.cache.cached
import io.androidalatan.lifecycle.template.valueholder.observeAsState

object FoobarActivityUi {
    @Composable
    fun Content(viewModel: FoobarActivityViewModel = cached()) {
        val viewInteractionTrigger = LocalComposeEventTriggerOwner.current
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = viewInteractionTrigger.onClick(FoobarActivityViewModel.clickId)) {
                Text(text = "Get Image from Gallery")
            }

            val imageUrl by viewModel.imageUrl.observeAsState()

            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            ) {
                if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                        Text(text = "Loading...")
                    }
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }

}