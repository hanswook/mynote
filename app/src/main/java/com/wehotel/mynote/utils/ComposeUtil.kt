package com.wehotel.mynote.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @date:     2019-11-25 14:00
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}


fun Long.sdf(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    return sdf.format(this)
}

fun Long.hms_sdf(): String {
    return SimpleDateFormat("HH:mm:ss").format(this)
}

fun Long.timedf(): String {
    return FriendlyTimeUtil.convertTimeToFormat(this)
}