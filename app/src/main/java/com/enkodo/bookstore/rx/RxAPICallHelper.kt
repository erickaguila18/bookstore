package com.enkodo.bookstore.rx

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class RxAPICallHelper {
    fun <T> call(observable: Observable<T>?, rxAPICallback: RxAPICallback<T>?): Disposable {
        if (observable == null) {
            throw IllegalArgumentException("Observable must not be null.")
        }


        if (rxAPICallback == null) {
            throw IllegalArgumentException("Callback must not be null.")
        }

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t -> rxAPICallback.onSuccess(t) }, { throwable ->
                    rxAPICallback.onFailed(throwable)
                })
    }
}