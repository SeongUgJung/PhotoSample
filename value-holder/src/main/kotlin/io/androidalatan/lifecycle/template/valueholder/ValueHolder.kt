package io.androidalatan.lifecycle.template.valueholder

interface ValueHolder<T> {
    fun get(): T

    fun registerObserver(callback: Callback<T>)

    fun unregisterObserver(callback: Callback<T>)

    fun interface Callback<T> {
        fun onValueUpdate(value: T)
    }

    fun update(body: (T) -> T): ValueHolder<T>

    companion object {
        operator fun <T> invoke(value: T): ValueHolder<T> {
            return object : ValueHolder<T> {
                private var _value = value
                private val callbacks by lazy { mutableListOf<ValueHolder.Callback<T>>() }
                override fun get(): T = _value

                override fun update(body: (T) -> T): ValueHolder<T> {
                    _value = body(_value)
                    callbacks.forEach {
                        it.onValueUpdate(_value)
                    }
                    return this
                }

                override fun unregisterObserver(callback: Callback<T>) {
                    callbacks.remove(callback)
                }

                override fun registerObserver(callback: Callback<T>) {
                    callbacks.add(callback)
                }
            }
        }
    }

}