package io.androidalatan.lifecycle.template.foobar.sub.bar

import androidx.compose.runtime.Composable
import io.androidalatan.compose.holder.ComposableHolder
import io.androidalatan.compose.holder.di.ComposableHolderComponent
import io.androidalatan.lifecycle.handler.compose.cache.composeCached
import javax.inject.Inject

class Bar(componentBuilder: ComposableHolderComponent.Builder<*>) :
    ComposableHolder(componentBuilder) {
    @set:Inject
    var barViewModel: BarViewModel by composeCached()

    @Composable
    override fun render() = BarUi.Content()
}