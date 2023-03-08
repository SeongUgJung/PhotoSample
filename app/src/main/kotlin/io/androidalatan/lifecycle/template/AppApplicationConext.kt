package io.androidalatan.lifecycle.template

import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.androidalatan.lifecycle.handler.invokeradapter.api.InvokeAdapterInitializer
import io.androidalatan.lifecycle.handler.invokeradapter.flow.FlowInvokeAdapter
import io.androidalatan.lifecycle.template.di.DaggerTemplateAppComponent
import io.androidalatan.router.JourneyMapper
import io.androidalatan.router.api.GuidanceBuilderFactory
import io.androidalatan.router.api.JourneyGuidance
import javax.inject.Inject

class AppApplicationConext : DaggerApplication() {

    @Inject
    lateinit var journeyGuidances: Map<Class<out JourneyGuidance>, @JvmSuppressWildcards GuidanceBuilderFactory>

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTemplateAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        InvokeAdapterInitializer.initialize(
            listOf(FlowInvokeAdapter.Factory())
        ) { /*log error */ }

        JourneyMapper.setJourneys(journeyGuidances)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}