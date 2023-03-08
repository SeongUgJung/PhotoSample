package io.androidalatan.lifecycle.template.main.di

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.androidalatan.lifecycle.template.main.MainActivity

@Module(
    subcomponents = [MainActivityComponent::class],
    includes = [MainActivityInjectorModule.InnerModule::class]
)
interface MainActivityInjectorModule {
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    fun bindOrderDetailActivityAndroidInjectorFactory(builder: MainActivityComponent.Builder): AndroidInjector.Factory<*>

    @Module
    object InnerModule
}