package io.androidalatan.lifecycle.template.foobar

import io.androidalatan.lifecycle.template.foobar.api.FoobarActivityJourneyGuidance
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.JourneyBuilder

class FoobarActivityJourneyGuidanceImpl(journeyBuilder: JourneyBuilder) :
    FoobarActivityJourneyGuidance {
    private val activityIntentJourneyBuilder by lazy {
        journeyBuilder.activity(FoobarActivity::class.java)
    }

    override fun create(): ComponentJourney = activityIntentJourneyBuilder.build()
}