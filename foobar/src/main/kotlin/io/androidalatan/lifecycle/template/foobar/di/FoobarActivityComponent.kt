package io.androidalatan.lifecycle.template.foobar.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import io.androidalatan.compose.dialog.api.ComposeAlertDialogBuilderFactory
import io.androidalatan.lifecycle.dagger.scope.annotations.ActivityScope
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.handler.compose.activity.di.ComposeLifecycleActivityBuilder
import io.androidalatan.lifecycle.template.foobar.FoobarActivity
import io.androidalatan.lifecycle.template.foobar.FoobarActivityViewModel
import io.androidalatan.lifecycle.template.foobar.usecase.ImageFetcher
import io.androidalatan.lifecycle.template.foobar.usecase.SaveImageUsecaseImpl

@ActivityScope
@Subcomponent(
    modules = [
        FoobarActivityComponent.FoobarActivityModule::class
    ]
)
interface FoobarActivityComponent : AndroidInjector<FoobarActivity> {
    @Subcomponent.Builder
    abstract class Builder : ComposeLifecycleActivityBuilder<FoobarActivity>()

    @Module()
    object FoobarActivityModule {
        @Provides
        @ActivityScope
        fun viewModel(
            lifecycleSource: LifecycleSource,
            alertDialogBuilderFactory: ComposeAlertDialogBuilderFactory
        ) =
            FoobarActivityViewModel(
                lifecycleSource,
                ImageFetcher(),
                SaveImageUsecaseImpl(),
                alertDialogBuilderFactory
            )
    }
}