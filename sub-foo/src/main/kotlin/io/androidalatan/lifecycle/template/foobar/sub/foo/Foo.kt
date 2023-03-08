package io.androidalatan.lifecycle.template.foobar.sub.foo

import androidx.compose.runtime.Composable
import io.androidalatan.compose.holder.ComposableHolder
import io.androidalatan.compose.holder.di.ComposableHolderComponent
import io.androidalatan.lifecycle.handler.compose.cache.composeCached
import javax.inject.Inject

class Foo(
    componentBuilder: ComposableHolderComponent.Builder<*>
) : ComposableHolder(componentBuilder) {

    @set:Inject
    var viewModel: FooViewModel by composeCached()

    @Composable
    override fun render() = FooUi.Content()
}