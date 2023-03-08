package io.androidalatan.lifecycle.template.foobar.sub.foo.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import io.androidalatan.compose.holder.di.ComposableHolderComponent
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.template.foobar.sub.foo.Foo
import io.androidalatan.lifecycle.template.foobar.sub.foo.FooViewModel
import javax.inject.Scope

@Scope
annotation class FooScope


@FooScope
@Subcomponent(
    modules = [
        FooComponent.FooComposableHolderModule::class
    ]
)
interface FooComponent : ComposableHolderComponent<Foo> {
    @Subcomponent.Builder
    abstract class Builder : ComposableHolderComponent.Builder<FooComponent>

    @Module
    object FooComposableHolderModule {
        @FooScope
        @Provides
        fun fooViewModel(lifecycleSource: ChildLifecycleSource) = FooViewModel(lifecycleSource)
    }
}