package io.androidalatan.lifecycle.template.foobar.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.androidalatan.lifecycle.template.foobar.FoobarActivity
import io.androidalatan.lifecycle.template.foobar.FoobarActivityJourneyGuidanceImpl
import io.androidalatan.lifecycle.template.foobar.api.FoobarActivityJourneyGuidance
import io.androidalatan.lifecycle.template.foobar.image.PickImageGalleryJourneyGuidance
import io.androidalatan.lifecycle.template.foobar.image.PickImageGalleryJourneyGuidanceImpl
import io.androidalatan.router.api.GuidanceBuilderFactory
import io.androidalatan.router.api.JourneyKey

@Module(
    subcomponents = [FoobarActivityComponent::class],
    includes = [FoobarActivityInjectorModule.InnerModule::class]
)
interface FoobarActivityInjectorModule {
    @Binds
    @IntoMap
    @ClassKey(FoobarActivity::class)
    fun bindOrderDetailActivityAndroidInjectorFactory(builder: FoobarActivityComponent.Builder): AndroidInjector.Factory<*>

    @Module
    object InnerModule {
        @Provides
        @IntoMap
        @JourneyKey(FoobarActivityJourneyGuidance::class)
        fun foobarActivityJourneyGuidance(): GuidanceBuilderFactory =
            GuidanceBuilderFactory { FoobarActivityJourneyGuidanceImpl(it) }

        @Provides
        @IntoMap
        @JourneyKey(PickImageGalleryJourneyGuidance::class)
        fun pickImageGalleryJourneyGuidance(): GuidanceBuilderFactory =
            GuidanceBuilderFactory { PickImageGalleryJourneyGuidanceImpl(it) }
    }
}