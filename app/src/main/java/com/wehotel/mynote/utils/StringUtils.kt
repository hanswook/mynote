package com.wehotel.mynote.utils

/**
 *
 * @date:     2019-11-28 18:39
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
object StringUtils {
    @JvmStatic
    fun trimToEmpty(id: String?): String {
        return id ?: ""
    }

}