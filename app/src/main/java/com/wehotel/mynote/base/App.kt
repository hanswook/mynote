package com.wehotel.mynote.base

import android.app.Application
import io.paperdb.Paper

/**
 *
 * @date:     2019-11-28 14:06
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
class App : Application() {

    companion object{
        var instance:App?=null
    }


    override fun onCreate() {
        super.onCreate()
        instance=this
        Paper.init(this)
    }

}