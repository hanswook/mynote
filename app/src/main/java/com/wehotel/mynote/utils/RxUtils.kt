package com.wehotel.mynote.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @date:     2019-11-26 14:04
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
object RxUtils {
    fun <T> applySchedulers(): ObservableTransformer<T, T>? {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}