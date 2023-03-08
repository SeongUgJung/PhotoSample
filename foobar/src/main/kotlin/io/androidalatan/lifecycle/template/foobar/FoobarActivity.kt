package io.androidalatan.lifecycle.template.foobar

import androidx.compose.runtime.Composable
import io.androidalatan.lifecycle.handler.compose.activity.ComposeLifecycleDaggerActivity
import io.androidalatan.lifecycle.handler.compose.cache.composeCached
import javax.inject.Inject

class FoobarActivity: ComposeLifecycleDaggerActivity() {
    @set:Inject
    var viewModel: FoobarActivityViewModel by composeCached()

    @Composable
    override fun contentView() = FoobarActivityUi.Content()
}