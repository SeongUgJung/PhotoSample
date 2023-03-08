package io.androidalatan.lifecycle.template.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.androidalatan.lifecycle.template.AppApplicationConext
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        TemplateAppModule::class
    ]
)
interface TemplateAppComponent: AndroidInjector<AppApplicationConext> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): TemplateAppComponent
    }
}