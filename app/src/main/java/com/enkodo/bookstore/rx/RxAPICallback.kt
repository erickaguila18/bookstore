package com.enkodo.bookstore.rx

interface RxAPICallback<P> {
    fun onSuccess(items: P)

    fun onFailed(throwable: Throwable)
}