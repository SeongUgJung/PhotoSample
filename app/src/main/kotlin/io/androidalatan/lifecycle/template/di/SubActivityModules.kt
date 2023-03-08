package io.androidalatan.lifecycle.template.di

import dagger.Module
import io.androidalatan.lifecycle.template.foobar.di.FoobarActivityInjectorModule
import io.androidalatan.lifecycle.template.main.di.MainActivityInjectorModule

@Module(
    includes = [
        MainActivityInjectorModule::class,
        FoobarActivityInjectorModule::class,
    ]
)
class SubActivityModules