package io.androidalatan.lifecycle.template.foobar

import android.app.Activity
import app.cash.turbine.test
import io.androidalatan.bundle.assertion.MapIntentData
import io.androidalatan.compose.dialog.assertion.MockAlertDialogBuilderFactory
import io.androidalatan.lifecycle.handler.assertion.MockLifecycleSource
import io.androidalatan.lifecycle.template.foobar.image.PickImageGalleryJourneyGuidance
import io.androidalatan.lifecycle.template.foobar.usecase.ImageFetcher
import io.androidalatan.lifecycle.template.foobar.usecase.SaveImageUsecase
import io.androidalatan.result.handler.assertion.MockResultInfo
import io.androidalatan.result.handler.assertion.MockResultStream
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.assertion.MockComponentJourney
import io.androidalatan.router.assertion.MockRouter
import io.androidalatan.view.event.assertions.MockViewInteractionStream
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever

class FoobarActivityViewModelTest {

    private val imageFetcher = ImageFetcher()
    private val saveImageUsecase = mock<SaveImageUsecase>()
    private val dialogBuilderFactory = MockAlertDialogBuilderFactory()
    private val viewModel = FoobarActivityViewModel(
        MockLifecycleSource(),
        imageFetcher,
        saveImageUsecase,
        dialogBuilderFactory
    )

    @AfterEach
    fun tearDown() {
        reset(saveImageUsecase)
    }

    @Test
    fun fetchFirstImage_success() = runTest {
        imageFetcher.success = true
        viewModel.fetchFirstImage().test {
            val url = awaitItem()
            assertThat(url).isNotEmpty
            awaitComplete()
            cancelAndIgnoreRemainingEvents()

            assertThat(viewModel.imageUrl.get())
                .isEqualTo(url)

        }
    }

    @Test
    fun fetchFirstImage_fail() = runTest {
        imageFetcher.success = false
        viewModel.fetchFirstImage().test {
            val url = awaitItem()
            assertThat(url).isEmpty()
            awaitComplete()
            cancelAndIgnoreRemainingEvents()

            assertThat(viewModel.imageUrl.get())
                .isEqualTo("")

            assertThat(dialogBuilderFactory.createCount).isOne()
            assertThat(dialogBuilderFactory.lastDialogBuilder).isNotNull
            assertThat(dialogBuilderFactory.lastDialogBuilder!!.message).isNotNull
                .isEqualTo("Failed to fetch image. Please check 4G, 5G or Wifi connection")
            assertThat(dialogBuilderFactory.lastDialogBuilder!!.buildCount).isOne
            assertThat(dialogBuilderFactory.lastDialogBuilder!!.lastBuiltDialog).isNotNull
            assertThat(dialogBuilderFactory.lastDialogBuilder!!.lastBuiltDialog!!.showCount).isOne

            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun goToGallery() = runTest {
        val viewInteractionStream = MockViewInteractionStream()
        val router = MockRouter()
        viewModel.goToGallery(viewInteractionStream, router)
            .test {
                expectNoEvents()

                val journey = MockComponentJourney()
                router.putJourneyGuidance(
                    PickImageGalleryJourneyGuidance::class,
                    object : PickImageGalleryJourneyGuidance {
                        override fun create(): ComponentJourney = journey
                    })

                viewInteractionStream.viewController
                    .trigger(FoobarActivityViewModel.clickId)
                    .click()

                assertThat(awaitItem()).isGreaterThanOrEqualTo(0L)
                assertThat(router.findCount[PickImageGalleryJourneyGuidance::class]).isOne
                assertThat(journey.visitCountForResult).isOne
                assertThat(journey.reqCode).isEqualTo(FoobarActivityViewModel.reqGallery)

                expectNoEvents()
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `updateImage wrong id`() = runTest {
        val resultStream = MockResultStream()
        viewModel.updateImage(resultStream)
            .test {
                expectNoEvents()
                resultStream.putResultInfo(
                    MockResultInfo(
                        FoobarActivityViewModel.reqGallery - 1,
                        Activity.RESULT_OK,
                        MapIntentData(mutableMapOf())
                    )
                )
                expectNoEvents()
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `updateImage correct id but cancelled`() = runTest {
        val resultStream = MockResultStream()
        viewModel.updateImage(resultStream)
            .test {
                expectNoEvents()
                resultStream.putResultInfo(
                    MockResultInfo(
                        FoobarActivityViewModel.reqGallery,
                        Activity.RESULT_CANCELED,
                        MapIntentData(mutableMapOf())
                    )
                )
                expectNoEvents()
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `updateImage overall ok but image wrong`() = runTest {
        val resultStream = MockResultStream()
        val oldUrl = "oldUrl-1"
        viewModel.imageUrl.update { oldUrl }
        viewModel.updateImage(resultStream)
            .test {
                expectNoEvents()

                resultStream.putResultInfo(
                    MockResultInfo(
                        FoobarActivityViewModel.reqGallery,
                        Activity.RESULT_OK,
                        MapIntentData(mutableMapOf())
                    )
                )

                expectNoEvents()
                cancelAndIgnoreRemainingEvents()

                assertThat(viewModel.imageUrl.get()).isEqualTo(oldUrl)

                assertThat(dialogBuilderFactory.createCount).isOne()
                assertThat(dialogBuilderFactory.lastDialogBuilder).isNotNull
                assertThat(dialogBuilderFactory.lastDialogBuilder!!.message).isEqualTo("something wrong about image picker")
                assertThat(dialogBuilderFactory.lastDialogBuilder!!.buildCount).isOne()
                assertThat(dialogBuilderFactory.lastDialogBuilder!!.lastBuiltDialog!!).isNotNull
                assertThat(dialogBuilderFactory.lastDialogBuilder!!.lastBuiltDialog!!.showCount).isOne()
            }
    }

    @Test
    fun `updateImage overall ok but upload wrong`() = runTest {
        val resultStream = MockResultStream()
        val oldUrl = "oldUrl-1"
        viewModel.imageUrl.update { oldUrl }
        viewModel.updateImage(resultStream)
            .test {
                expectNoEvents()

                whenever(saveImageUsecase.upload(any())).thenReturn(flow {
                    throw IllegalStateException(
                        "error"
                    )
                })

                resultStream.putResultInfo(
                    MockResultInfo(
                        FoobarActivityViewModel.reqGallery,
                        Activity.RESULT_OK,
                        MapIntentData(mutableMapOf(), uriString = "asdad")
                    )
                )

                expectNoEvents()
                cancelAndIgnoreRemainingEvents()

                assertThat(viewModel.imageUrl.get()).isEqualTo(oldUrl)

                assertThat(dialogBuilderFactory.createCount).isOne()
                assertThat(dialogBuilderFactory.lastDialogBuilder).isNotNull
                assertThat(dialogBuilderFactory.lastDialogBuilder!!.message).isEqualTo("something wrong about image picker")
                assertThat(dialogBuilderFactory.lastDialogBuilder!!.buildCount).isOne()
                assertThat(dialogBuilderFactory.lastDialogBuilder!!.lastBuiltDialog!!).isNotNull
                assertThat(dialogBuilderFactory.lastDialogBuilder!!.lastBuiltDialog!!.showCount).isOne()
            }
    }

    @Test
    fun `updateImage happy`() = runTest {
        val resultStream = MockResultStream()
        viewModel.updateImage(resultStream)
            .test {
                expectNoEvents()

                val newUrl = "asdad"
                whenever(saveImageUsecase.upload(any())).thenReturn(flowOf(newUrl))

                resultStream.putResultInfo(
                    MockResultInfo(
                        FoobarActivityViewModel.reqGallery,
                        Activity.RESULT_OK,
                        MapIntentData(mutableMapOf(), uriString = "123asd")
                    )
                )

                assertThat(awaitItem()).isEqualTo(newUrl)
                cancelAndIgnoreRemainingEvents()

                assertThat(viewModel.imageUrl.get()).isEqualTo(newUrl)
            }
    }
}