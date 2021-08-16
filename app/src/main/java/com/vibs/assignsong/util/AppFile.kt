package com.vibs.assignsong.util

import android.content.Context
import android.content.SharedPreferences
import com.vibs.assignsong.R

class AppFile {
    companion object {
        var appFile: AppFile? = null
        const val KEY_FIRST_TIME = "KEY_FIRST_TIME"
    }
    lateinit var sharePef: SharedPreferences
    fun getInstance(context: Context): AppFile {
        if (appFile == null) {
            appFile = AppFile()
            sharePef = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        return appFile!!
    }

    fun setInt(key: String, data: Int) {
        sharePef.edit().putInt(key, data).apply()
    }

    fun getInt(key: String): Int {
        return sharePef.getInt(key, -1)
    }
}