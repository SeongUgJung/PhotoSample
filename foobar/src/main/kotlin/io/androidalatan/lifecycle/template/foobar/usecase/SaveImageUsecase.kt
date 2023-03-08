package io.androidalatan.lifecycle.template.foobar.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SaveImageUsecase {
    fun upload(imageUri: String): Flow<String>
}

class SaveImageUsecaseImpl : SaveImageUsecase {
    override fun upload(imageUri: String): Flow<String> {
        return flow {
            // save image in virtual
            delay(3000)
            emit(randomImage())
        }
    }

}