package io.androidalatan.lifecycle.template.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.template.foobar.api.FoobarActivityJourneyGuidance
import io.androidalatan.router.api.Router
import io.androidalatan.view.event.api.ViewInteractionStream
import io.androidalatan.view.event.legacy.flow.asFlow
import io.androidalatan.view.event.legacy.flow.view.onClickAsFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MainActivityViewModel(lifecycleSource: LifecycleSource) : LifecycleListener(lifecycleSource) {

    val resumeText = MutableLiveData<String>()
    val createText = MutableLiveData<String>()

    @ResumedToPause
    fun endlessCounting(): Flow<String> {
        return (0..Int.MAX_VALUE).asFlow()
            .onEach { delay(1000) }
            .map { "ResumedToPause/endlessCounting : $it" }
            .onEach {
                resumeText.value = it
            }
    }

    @CreatedToDestroy
    fun endlessCounting2(): Flow<String> {
        return (0..Int.MAX_VALUE).asFlow()
            .onEach { delay(500) }
            .map { "CreatedToDestroy/endlessCounting : $it" }
            .onEach {
                createText.value = it
            }
    }

    @ResumedToPause
    fun observeClick(viewInteractionStream: ViewInteractionStream, router: Router): Flow<Long> {
        return viewInteractionStream.asFlow()
            .flatMapLatest { it.find(clickId).onClickAsFlow() }
            .onEach {
                router.findOrNull(FoobarActivityJourneyGuidance::class)
                    ?.create()
                    ?.visit()
            }
    }

    companion object {
        const val clickId = 123
    }
}