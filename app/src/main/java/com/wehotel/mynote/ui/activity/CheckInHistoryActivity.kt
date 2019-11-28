package com.wehotel.mynote.ui.activity

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wehotel.mynote.R
import com.wehotel.mynote.base.BaseActivity
import com.wehotel.mynote.data.CheckInBean
import com.wehotel.mynote.data.Const
import com.wehotel.mynote.data.Const.BOOK_HISTORY
import com.wehotel.mynote.data.Const.HISTORY_KEY
import com.wehotel.mynote.ui.list.HistoryAdapter
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_check_in_history.*

class CheckInHistoryActivity : BaseActivity() {


    override fun getLayoutId(): Int = R.layout.activity_check_in_history

    companion object {
        fun goToPage(context: Context) {
            context.startActivity(Intent(context, CheckInHistoryActivity::class.java))
        }
    }

    private val historyData: ArrayList<CheckInBean> by lazy {
        Paper.book(BOOK_HISTORY).read<ArrayList<CheckInBean>>(Const.HISTORY_KEY) ?: ArrayList<CheckInBean>()
    }


    override fun initBiz() {
        initList()
        initShow()
    }

    private val historyAdapter by lazy { HistoryAdapter() }


    private fun initList() {
        rv_history.adapter = historyAdapter
        rv_history.layoutManager = LinearLayoutManager(mActivity)
        rv_history.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        historyAdapter.setOnItemLongClickListener { adapter, view, position ->
            deleteHistory(position)
            return@setOnItemLongClickListener true
        }
    }

    private fun initShow() {

        println("read:${historyData.size}")
        historyAdapter.replaceData(historyData)
        println("historyAdapter:${historyAdapter.data.size}")

    }

    private fun deleteHistory(index: Int) {
        println("historyData:${historyAdapter.data.size}")
        historyAdapter.data.removeAt(index)
        historyAdapter.notifyDataSetChanged()
        Paper.book(BOOK_HISTORY).write(HISTORY_KEY, historyAdapter.data)
    }

}
