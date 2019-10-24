package com.kavak.brastlewark.util

import android.content.Context
import android.os.Build
import androidx.preference.PreferenceManager
import java.lang.Exception


object SharedPreferencesManager {

    operator fun <T> get(context: Context, key: String, defValue: T): T {
        return try {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            sharedPreferences.all[key] as T ?: defValue
        } catch (ex: Exception) {
            defValue
        }
    }

    operator fun set(context: Context, key: String, value: Any) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        when (value) {
            is Boolean -> editor.putBoolean(key, value).apply()
            is Int -> editor.putInt(key, value).apply()
            is String -> editor.putString(key, value).apply()
            is Long -> editor.putLong(key, value).apply()
            is Float -> editor.putFloat(key, value).apply()
            is Set<*> -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                try {
                    editor.putStringSet(key, value as MutableSet<String>?).apply()
                } catch (e: Exception) {
                    return
                }
            }
        }
    }
}
