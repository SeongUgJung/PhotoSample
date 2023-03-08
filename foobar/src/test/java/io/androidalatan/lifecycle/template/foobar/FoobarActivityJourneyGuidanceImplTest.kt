package io.androidalatan.lifecycle.template.foobar

import io.androidalatan.router.assertion.builder.MockJourneyBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FoobarActivityJourneyGuidanceImplTest {

    @Test
    fun create() {
        val journeyBuilder = MockJourneyBuilder()
        val journey = FoobarActivityJourneyGuidanceImpl(journeyBuilder)
            .create()

        assertThat(journeyBuilder.destinationJourneyBuilder?.buildCount).isOne()
        assertThat(journeyBuilder.destinationJourneyBuilder?.builtJourney).isEqualTo(journey)
    }
}