package io.androidalatan.lifecycle.template.foobar.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageFetcher {
    var success = true

    fun fetchImageUrl(): Flow<String> {
        return flow {
            // call api in virtual
            delay(4000)
            if (success) {
                emit(randomImage())
            } else {
                throw IllegalStateException("failed to fetch image")
            }
        }
    }
}

private var imageStep = 0

private val images = listOf(
    "https://picsum.photos/640",
    "https://picsum.photos/720",
    "https://picsum.photos/1080"
)


fun randomImage(): String {
    return images[imageStep++ % images.size]
}
