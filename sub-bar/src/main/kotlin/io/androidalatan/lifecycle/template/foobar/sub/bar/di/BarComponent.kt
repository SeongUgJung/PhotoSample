package io.androidalatan.lifecycle.template.foobar.sub.bar.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.androidalatan.compose.holder.di.ComposableHolderComponent
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource
import io.androidalatan.lifecycle.template.foobar.sub.bar.Bar
import io.androidalatan.lifecycle.template.foobar.sub.bar.BarViewModel
import javax.inject.Scope


@Scope
annotation class BarScope

@BarScope
@Subcomponent(
    modules = [
        BarComponent.BarModule::class
    ]
)
interface BarComponent : ComposableHolderComponent<Bar> {
    @Subcomponent.Builder
    abstract class Builder : ComposableHolderComponent.Builder<BarComponent>

    @Module
    object BarModule {
        @BarScope
        @Provides
        fun barViewModel(lifecycleSource: ChildLifecycleSource) = BarViewModel(lifecycleSource)
    }
}