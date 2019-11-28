package com.wehotel.mynote.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar

/**
 *
 * @date:     2019-11-28 14:21
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val mActivity by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).init()
        setContentView(getLayoutId())
        initBiz()
    }

    abstract fun getLayoutId(): Int

    abstract fun initBiz()
}