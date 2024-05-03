package edu.quinnipiac.ser210.tasks

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tasks.Task
import kotlinx.coroutines.launch

class TasksViewModel(val dao: TaskDAO) : ViewModel() {
    var newTaskName = ""

    val tasks = dao.getAll()
    val tasksString = Transformations.map(tasks) {
            tasks -> formatTasks(tasks)
    }

    fun addTask() {
        viewModelScope.launch {
            try {
                val task = Task()
                task.taskName = newTaskName
                dao.insert(task)
            } catch (e: Exception) {
                // Handle the error appropriately
            }
//            val task = Task()
//            task.taskName = newTaskName
//            dao.insert(task)
        }
    }

    fun formatTasks(tasks: List<Task>): String{
        return tasks.fold(""){
                str, item -> str + '\n' + formatTask(item)
        }
    }

    fun formatTask(task: Task): String {
        var str = "ID ${task.taskId}"
        str += '\n' + "Name: ${task.taskName}"
        str += '\n' + "Complete: ${task.taskDone}" + '\n'
        return str
    }
}