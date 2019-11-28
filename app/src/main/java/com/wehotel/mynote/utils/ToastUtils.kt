package com.wehotel.mynote.utils

import com.wehotel.mynote.base.App

/**
 *
 * @date:     2019-11-28 18:32
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
object ToastUtils {

    @JvmStatic
    fun show(msg: String) {
        msg.toast(App.instance!!.applicationContext)
    }
}