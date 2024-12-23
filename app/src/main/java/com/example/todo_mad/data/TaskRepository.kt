package com.example.todo_mad.data

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.example.todo_mad.utill.TaskWidgetProvider
import com.example.todo_mad.model.Task
import com.example.todo_mad.utill.SharedPreferencesHelper

class TaskRepository(private val context: Context) {

    fun addTask(task: Task) {
        // Load the last task ID from SharedPreferences
        var lastTaskId = SharedPreferencesHelper.loadLastTaskId(context)

        // Increment the task ID
        lastTaskId += 1

        // Set the new ID to the task
        task.id = lastTaskId

        // Save the updated ID to SharedPreferences
        SharedPreferencesHelper.saveLastTaskId(lastTaskId, context)

        val taskList = LocalStorageHelper.loadTasksFromFile(context).toMutableList()
        taskList.add(task)
        LocalStorageHelper.saveTasksToFile(taskList, context)
    }

    fun editTask(updatedTask: Task) {
        val taskList = LocalStorageHelper.loadTasksFromFile(context).toMutableList()
        val taskIndex = taskList.indexOfFirst { it.id == updatedTask.id }
        if (taskIndex != -1) {
            taskList[taskIndex] = updatedTask
            LocalStorageHelper.saveTasksToFile(taskList, context)
        }
    }

    fun deleteTask(taskId: Int) {
        val taskList = LocalStorageHelper.loadTasksFromFile(context).toMutableList()

        // Find the task with the specified ID
        val taskToRemove = taskList.find { it.id == taskId }

        // Remove the task if found
        if (taskToRemove != null) {
            taskList.remove(taskToRemove)
            LocalStorageHelper.saveTasksToFile(taskList, context)
        }
    }

    fun getAllTasks(): List<Task> {
        return LocalStorageHelper.loadTasksFromFile(context)
    }

    fun updateWidget(context: Context) {
        val intent = Intent(context, TaskWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context, TaskWidgetProvider::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(intent)
    }
}
