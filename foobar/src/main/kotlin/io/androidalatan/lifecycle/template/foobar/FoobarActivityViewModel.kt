package io.androidalatan.lifecycle.template.foobar

import android.app.Activity
import android.util.Log
import io.androidalatan.compose.dialog.api.ComposeAlertDialogBuilderFactory
import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.template.foobar.image.PickImageGalleryJourneyGuidance
import io.androidalatan.lifecycle.template.foobar.usecase.ImageFetcher
import io.androidalatan.lifecycle.template.foobar.usecase.SaveImageUsecase
import io.androidalatan.lifecycle.template.valueholder.ValueHolder
import io.androidalatan.result.handler.api.ResultStream
import io.androidalatan.result.handler.flow.adapter.resultInfoAsFlow
import io.androidalatan.router.api.Router
import io.androidalatan.view.event.api.ViewInteractionStream
import io.androidalatan.view.event.legacy.flow.asFlow
import io.androidalatan.view.event.legacy.flow.view.onClickAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample

class FoobarActivityViewModel(
    lifecycleSource: LifecycleSource,
    private val imageFetcher: ImageFetcher,
    private val saveImageUsecase: SaveImageUsecase,
    private val alertDialogBuilderFactory: ComposeAlertDialogBuilderFactory
) : LifecycleListener(lifecycleSource) {

    val imageUrl = ValueHolder("")

    @CreatedToDestroy
    fun fetchFirstImage(): Flow<String> {
        return imageFetcher.fetchImageUrl()
            .onEach { newImageUrl ->
                imageUrl.update { newImageUrl }
            }
            .catch {
                alertDialogBuilderFactory.create()
                    .setMessage("Failed to fetch image. Please check 4G, 5G or Wifi connection")
                    .build()
                    .show()
                emit("")
            }
    }

    @ResumedToPause
    fun goToGallery(viewInteractionStream: ViewInteractionStream, router: Router): Flow<Long> {
        return viewInteractionStream.asFlow()
            .flatMapLatest {
                it.find(clickId).onClickAsFlow()
                    .sample(300)
                    .onEach {
                        router.findOrNull(PickImageGalleryJourneyGuidance::class)
                            ?.create()
                            ?.visitForResult(reqGallery)
                    }
            }
    }

    @CreatedToDestroy
    fun updateImage(resultStream: ResultStream): Flow<Any> {
        return resultStream.resultInfoAsFlow(reqGallery)
            .flatMapLatest { result ->
                if (result.resultCode() == Activity.RESULT_OK) {

                    flow {
                        emit(
                            result.resultData().getUriStringOrNull()
                                ?: throw IllegalArgumentException(
                                    "wrong image"
                                )
                        )
                    }.flatMapLatest {
                        saveImageUsecase.upload(it)
                            .onEach { newImageUrl -> imageUrl.update { newImageUrl } }

                    }.catch {
                        alertDialogBuilderFactory.create()
                            .setMessage("something wrong about image picker")
                            .build()
                            .show()
                    }

                } else {
                    emptyFlow()
                }
            }
    }

    companion object {
        const val clickId = 231
        const val reqGallery = 39278


    }
}