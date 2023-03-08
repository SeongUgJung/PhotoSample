package io.androidalatan.lifecycle.template.foobar.sub.foo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class FooViewModel(lifecycleSource: LifecycleSource) :
    LifecycleListener(lifecycleSource) {

    val text = MutableLiveData<String>()

    @CreatedToDestroy
    fun endlessCounting(): Flow<String> {
        return (0..Int.MAX_VALUE).asFlow()
            .onEach { delay(1000) }
            .map { "FooViewModel.endlessCounting() : $it" }
            .onEach {
                text.value = it
            }
    }
}