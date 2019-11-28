package com.wehotel.mynote.utils

import java.util.*
import java.util.Calendar.HOUR_OF_DAY


/**
 *
 * @date:     2019-11-28 15:43
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
object FriendlyTimeUtil {


    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    fun convertTimeToFormat(timeStamp: Long): String {
        val curTime = System.currentTimeMillis() / 1000.toLong()
        val time = curTime - (timeStamp / 1000)
        return if (time in 0..59) {
            "刚刚"
        } else if (time in 60..3599) {
            time.div(60).toString() + "分钟前"
        } else if (time >= 3600 && time < 3600 * 24) {
            time.div(3600).toString() + "小时前"
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            time.div(3600 * 24).toString() + "天前"
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            time.div(3600 * 24 * 30).toString() + "个月前"
        } else if (time >= 3600 * 24 * 30 * 12) {
            time.div(3600 * 24 * 30 * 12).toString() + "年前"
        } else {
            "刚刚"
        }
    }

    fun isToday(timeStamp: Long): Boolean {
        println("isToday timeStamp(0):${timeStamp}")
        return ((timeStamp > getTodayStart()) && (timeStamp < getTomorrowStart()))
    }

    private fun getTodayStart(): Long {
        println("getDayStart(0):${getDayStart(0)}")
        println("getDayStart(0):${getDayStart(0).sdf()}")
        return getDayStart(0)
    }

    private fun getTomorrowStart(): Long {
        println("getTomorrowStart(0):${getDayStart(1)}")
        println("getTomorrowStart(0):${getDayStart(1).sdf()}")

        return getDayStart(1)
    }

    private fun getDayStart(amount: Int): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH) + amount,
            0,
            0,
            0
        )
        return calendar.time.time
    }

    public fun getTimeStamp(hour: Int): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            hour,
            0,
            0
        )
        return calendar.time.time
    }

    public fun isAMCheckInTime(timeStamp: Long): Boolean {
        return timeStamp <= getTimeStamp(10)
    }

    public fun isPMCheckInTime(timeStamp: Long): Boolean {
        return timeStamp >= getTimeStamp(19)
    }

    public fun isWhatType(timeStamp: Long): Int {
        return when {
            isAMCheckInTime(timeStamp) -> {
                1
            }
            isPMCheckInTime(timeStamp) -> {
                2
            }
            else -> {
                0
            }
        }

    }

    public fun isWhatTypeString(timeStamp: Long): String {
        return when (isWhatType(timeStamp)) {
            1 -> {
                "AM"
            }
            2 -> {
                "PM"
            }
            else -> {
                "未知"
            }
        }

    }

    public fun timeIsWhatType(timeStamp: Long): Int {
        val hourOfDay = getHourOfDay(timeStamp)
        return when {
            hourOfDay <= 10 -> {
                1
            }
            hourOfDay >= 19 -> {
                2
            }
            else -> {
                0
            }
        }

    }


    public fun getType(timeStamp: Long): Boolean {
        return timeStamp < getTimeStamp(10)
    }

    public fun getHourOfDay(timeStamp: Long): Int {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        return calendar.get(HOUR_OF_DAY)
    }
}