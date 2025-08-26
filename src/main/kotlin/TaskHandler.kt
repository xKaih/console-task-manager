package io.github.xkaih
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File

//This will be my first time using JSON files so probably I won't use the correct practices
//Basically I will do simple CRUD for this.
class TaskHandler {

    //Basically collects all the data from the file to load the saved tasks
    constructor(){
        try {
            json.decodeFromString<MutableMap<Int, Task>>(File("tasks.json").readText()).toMap(tasks)
        }
         catch (e: Exception){
             //For the moment this won't do anything
         }
    }

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
    private val tasks = mutableMapOf<Int, Task>()

    private fun getTask(id: Int): Task? {
        return tasks[id]
    }

    fun getTasks(): Map<Int, Task> {
        return tasks.toMap() //Return a copy of the original map just for securance.
        // But probably not necessary cause lol this is a simple project. Anyway I think is a good practice
    }

    fun addTask(name: String, description: String, completed: Boolean = false) {
        tasks.put(tasks.size+1,Task(name, description, completed))
    }

    fun modifyTask(taskId: Int, name: String? = null, description: String? = null) {
        val task = getTask(taskId)

        task?.name = name ?: task.name
        task?.description = description ?: task.description
    }

    fun deleteTask(taskId: Int) {
        tasks.remove(taskId)
    }

    //I thought that this is simpler than complete and uncomplete separate functions
    fun alternateTaskState(taskId: Int) {
        val task = getTask(taskId)
        task?.completed = !task.completed
    }

    fun saveChanges() {
        val tasksString = json.encodeToString(getTasks())
        File("tasks.json").writeText(tasksString)
    }
}