package io.androidalatan.lifecycle.template.foobar.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import io.androidalatan.lifecycle.dagger.scope.annotations.ActivityScope
import io.androidalatan.lifecycle.handler.compose.activity.di.ComposeLifecycleActivityBuilder
import io.androidalatan.lifecycle.template.foobar.FoobarActivity
import io.androidalatan.lifecycle.template.foobar.sub.bar.Bar
import io.androidalatan.lifecycle.template.foobar.sub.bar.di.BarComponent
import io.androidalatan.lifecycle.template.foobar.sub.foo.Foo
import io.androidalatan.lifecycle.template.foobar.sub.foo.di.FooComponent

@ActivityScope
@Subcomponent(
    modules = [
        FoobarActivityComponent.FoobarActivityModule::class
    ]
)
interface FoobarActivityComponent : AndroidInjector<FoobarActivity> {
    @Subcomponent.Builder
    abstract class Builder : ComposeLifecycleActivityBuilder<FoobarActivity>()

    @Module(
        subcomponents = [
            FooComponent::class,
            BarComponent::class
        ]
    )
    object FoobarActivityModule {
        @Provides
        @ActivityScope
        fun foo(builder: FooComponent.Builder) = Foo(builder)

        @Provides
        @ActivityScope
        fun bar(builder: BarComponent.Builder) = Bar(builder)
    }
}