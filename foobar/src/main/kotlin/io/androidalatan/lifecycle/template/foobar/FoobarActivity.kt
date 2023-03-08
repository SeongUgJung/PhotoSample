package io.androidalatan.lifecycle.template.foobar

import androidx.compose.runtime.Composable
import io.androidalatan.lifecycle.handler.compose.activity.ComposeLifecycleDaggerActivity
import io.androidalatan.lifecycle.handler.compose.cache.composeCached
import io.androidalatan.lifecycle.template.foobar.sub.bar.Bar
import io.androidalatan.lifecycle.template.foobar.sub.foo.Foo
import javax.inject.Inject

class FoobarActivity: ComposeLifecycleDaggerActivity() {
    @set:Inject
    var foo: Foo by composeCached()

    @set:Inject
    var bar: Bar by composeCached()

    @Composable
    override fun contentView() = FoobarActivityUi.Content()
}