package io.androidalatan.lifecycle.template.foobar.image

import android.content.Intent
import io.androidalatan.router.api.ComponentJourneyGuidance
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.JourneyBuilder

interface PickImageGalleryJourneyGuidance : ComponentJourneyGuidance

class PickImageGalleryJourneyGuidanceImpl(private val journeyBuilder: JourneyBuilder) :
    PickImageGalleryJourneyGuidance {
    override fun create(): ComponentJourney {
        // will not build test code due to intent
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        return journeyBuilder.activityIntent(
            Intent.createChooser(intent, "Select Picture")
        ).build()
    }
}