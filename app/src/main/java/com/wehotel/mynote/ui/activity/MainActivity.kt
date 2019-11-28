package com.wehotel.mynote.ui.activity

import android.annotation.SuppressLint
import android.view.View
import com.wehotel.mynote.R
import com.wehotel.mynote.base.BaseActivity
import com.wehotel.mynote.data.CheckInBean
import com.wehotel.mynote.data.Const
import com.wehotel.mynote.data.Const.BOOK_HISTORY
import com.wehotel.mynote.data.Const.HISTORY_KEY
import com.wehotel.mynote.data.Const.checkInNow
import com.wehotel.mynote.utils.*
import io.paperdb.Paper
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initBiz() {

        initShow()
        checkIntent()
    }

    private val historyData: ArrayList<CheckInBean> by lazy {
        Paper.book(BOOK_HISTORY).read<ArrayList<CheckInBean>>(Const.HISTORY_KEY) ?: ArrayList<CheckInBean>()
    }


    fun checkIn(view: View?) {
        val currentTimeMillis = System.currentTimeMillis()
        if (FriendlyTimeUtil.timeIsWhatType(currentTimeMillis) == 0) {
            toast("不到时间不能打卡")
            return
        }
        historyData.add(CheckInBean(currentTimeMillis, FriendlyTimeUtil.timeIsWhatType(currentTimeMillis)))
        Paper.book(BOOK_HISTORY).write(HISTORY_KEY, historyData)
        checkinShow()
    }

    fun showHistory(view: View) {
        CheckInHistoryActivity.goToPage(mActivity)
    }


    private fun initShow() {
        startTimer()
        checkinShow()
        ShortCutUtils.createDynamic(
            mActivity,
            MainActivity::class.java, R.mipmap.icon_time, "打卡", "打卡_dynamic"
        )
        ShortCutUtils.create(
            mActivity,
            "打卡",
            MainActivity::class.java,
            R.mipmap.icon_time,
            "打卡_pin"
        )

    }

    @SuppressLint("CheckResult")
    private fun startTimer() {
        Observable.interval(0, 500, TimeUnit.MILLISECONDS)
            .compose(RxUtils.applySchedulers())
            .subscribe(
                {
                    tv_current_time.text = System.currentTimeMillis().hms_sdf()
                }, {

                })
    }


    private fun checkinShow() {
        var amcheckin = false
        var pmcheckin = false
        var amTimeBean: CheckInBean? = null
        var pmTimeBean: CheckInBean? = null

        for (item in historyData.reversed()) {
            if (FriendlyTimeUtil.isToday(item.checkInTime)) {
                when (FriendlyTimeUtil.isWhatType(item.checkInTime)) {
                    1 -> {
                        amcheckin = true
                        if (amTimeBean == null) {
                            amTimeBean = item
                        }
                    }
                    2 -> {
                        pmcheckin = true
                        if (pmTimeBean == null) {
                            pmTimeBean = item
                        }
                    }
                }
            } else {
                break
            }
        }

        val txt =
            "${if (amcheckin) "上班已打卡 打卡时间${amTimeBean?.checkInTime?.sdf()}" else "上班未打卡"}\n${if (pmcheckin) "下班已打卡 打卡时间${pmTimeBean?.checkInTime?.sdf()}" else "下班未打卡"}"
        tv_checkin_data.text = txt
    }


    private fun checkIntent() {
        val checkInNow = intent.getBooleanExtra(checkInNow, false)
        if (checkInNow) {
            checkIn(null)
        }
    }


}
