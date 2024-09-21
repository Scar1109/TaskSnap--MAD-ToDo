package com.example.todo_mad.utill

import android.content.Context

object SharedPreferencesHelper {

    private const val PREFS_NAME = "app_prefs"
    private const val LAST_TASK_ID_KEY = "last_task_id"

    // Save the last task ID
    fun saveLastTaskId(taskId: Int, context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(LAST_TASK_ID_KEY, taskId)
        editor.apply()
    }

    // Load the last task ID
    fun loadLastTaskId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(LAST_TASK_ID_KEY, 0) // Default to 0 if no ID is found
    }
}