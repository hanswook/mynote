package com.wehotel.mynote.data

/**
 *
 * @date:     2019-11-28 14:58
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
data class CheckInBean(
    val checkInTime: Long,
    //am 1, pm 2 ,unknow 0
    val amOrPm: Int
) {

}