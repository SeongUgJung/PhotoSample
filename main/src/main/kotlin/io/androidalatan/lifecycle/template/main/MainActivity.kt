package io.androidalatan.lifecycle.template.main

import androidx.compose.runtime.Composable
import io.androidalatan.lifecycle.handler.compose.activity.ComposeLifecycleDaggerActivity
import io.androidalatan.lifecycle.handler.compose.cache.composeCached
import javax.inject.Inject

class MainActivity : ComposeLifecycleDaggerActivity() {
    @set:Inject
    var viewModel: MainActivityViewModel by composeCached()

    @Composable
    override fun contentView() = MainActivityUi.Content()
}