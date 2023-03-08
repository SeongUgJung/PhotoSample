package io.androidalatan.lifecycle.template.main.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import io.androidalatan.lifecycle.dagger.scope.annotations.ActivityScope
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.handler.compose.activity.di.ComposeLifecycleActivityBuilder
import io.androidalatan.lifecycle.template.main.MainActivity
import io.androidalatan.lifecycle.template.main.MainActivityViewModel

@ActivityScope
@Subcomponent(
    modules = [
        MainActivityComponent.MainActivityModule::class
    ]
)
interface MainActivityComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : ComposeLifecycleActivityBuilder<MainActivity>()

    @Module
    object MainActivityModule {
        @Provides
        @ActivityScope
        fun mainActivityViewModel(lifecycleSource: LifecycleSource) =
            MainActivityViewModel(lifecycleSource)
    }
}