package com.wehotel.mynote.ui.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wehotel.mynote.R
import com.wehotel.mynote.data.CheckInBean
import com.wehotel.mynote.utils.FriendlyTimeUtil
import com.wehotel.mynote.utils.sdf
import com.wehotel.mynote.utils.timedf

/**
 *
 * @date:     2019-11-28 14:57
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
class HistoryAdapter :
    BaseQuickAdapter<CheckInBean, BaseViewHolder>(R.layout.list_item_checkin_history) {
    override fun convert(helper: BaseViewHolder, item: CheckInBean?) {
        item ?: return

        item.checkInTime

        helper.setText(R.id.tv_checkin_time, item.checkInTime.sdf())
        helper.setText(R.id.tv_amorpm, FriendlyTimeUtil.isWhatTypeString(item.checkInTime))

    }
}