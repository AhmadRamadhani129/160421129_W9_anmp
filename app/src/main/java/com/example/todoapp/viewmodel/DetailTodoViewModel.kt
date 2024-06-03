package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import com.example.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application):
    AndroidViewModel(application), CoroutineScope {

    private val job = Job()
    val todoLd = MutableLiveData<Todo>()

    fun addTodo(todo: Todo){
        launch {
            val db = buildDb(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetch(uuid: Int){
        launch {
            val db = buildDb(getApplication())
            todoLd.postValue(db.todoDao().selectTodo(uuid))
        }
    }

    fun update(title: String, notes:String, priority: Int, uuid: Int){
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(title, notes, priority, uuid)
        }
    }

    fun updateTodo(todo: Todo){
        launch {
            buildDb(getApplication()).todoDao().updateTodo(todo)
        }
    }
}