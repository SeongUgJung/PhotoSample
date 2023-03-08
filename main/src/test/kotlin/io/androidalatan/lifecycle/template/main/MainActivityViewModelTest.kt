package io.androidalatan.lifecycle.template.main

import app.cash.turbine.test
import io.androidalatan.lifecycle.handler.assertion.MockLifecycleSource
import io.androidalatan.lifecycle.template.foobar.api.FoobarActivityJourneyGuidance
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.assertion.MockComponentJourney
import io.androidalatan.router.assertion.MockJourneyGuidance
import io.androidalatan.router.assertion.MockRouter
import io.androidalatan.view.event.assertions.MockViewInteractionStream
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class MainActivityViewModelTest {

    private val viewModel = MainActivityViewModel(MockLifecycleSource())

    @Test
    fun observeClick() = runTest {
        val viewInteractionStream = MockViewInteractionStream()
        val router = MockRouter()
        val journey = MockComponentJourney()
        router.putJourneyGuidance(
            FoobarActivityJourneyGuidance::class,
            object : FoobarActivityJourneyGuidance {
                override fun create(): ComponentJourney = journey
            })
        viewModel.observeClick(viewInteractionStream, router)
            .test {
                expectNoEvents()

                viewInteractionStream.viewController.trigger(MainActivityViewModel.clickId)
                    .click()

                assertEquals(0, awaitItem())
                assertEquals(1, journey.visitCount)
                assertEquals(1, router.findCount[FoobarActivityJourneyGuidance::class])


            }
    }
}