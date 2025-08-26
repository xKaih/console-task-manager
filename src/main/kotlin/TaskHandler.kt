package io.github.xkaih
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File

//This will be my first time using JSON files so probably I won't use the correct practices
//Basically I will do simple CRUD for this.
class TaskHandler {



    private var nextId = 1

    private val file = File("tasks.json")
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
    private val tasks = mutableMapOf<Int, Task>()

    //Basically collects all the data from the file to load the saved tasks
    init {
        if (file.exists()) {
            try {
                val storage = json.decodeFromString<TaskStorage>(file.readText())
                tasks.putAll(storage.tasks)
                nextId = storage.nextId
            } catch (e: Exception) {
                println("Error loading tasks: ${e.message}")
            }
        }
    }

    private fun getTask(id: Int): Task? {
        return tasks[id]
    }

    fun getTasks(): Map<Int, Task> {
        return tasks.toMap() //Return a copy of the original map just for securance.
        // But probably not necessary cause lol this is a simple project. Anyway I think is a good practice
    }

    fun addTask(name: String, description: String, completed: Boolean = false) {
        tasks.put(nextId++, Task(name, description, completed))
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
        val storage = json.encodeToString(TaskStorage(nextId, tasks))
        File("tasks.json").writeText(storage)
    }
}