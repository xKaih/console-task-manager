package io.github.xkaih

fun printTasks(tasks: Map<Int, Task>) {
    for (task in tasks.toSortedMap()) {
        ConsoleHandler.printWithColor(task.key.toString() + ". ", AnsiColor.YELLOW)
            .printWithColor("[" + task.value.name + "] ", AnsiColor.PURPLE)
            .printWithColor(task.value.description + " ", AnsiColor.YELLOW)
            .printlnWithColor("X",if (task.value.completed) AnsiColor.GREEN else AnsiColor.RED).lineBreak()
    }
}

//I don't know how to call this function
fun printAsks() : Array<String>{
    ConsoleHandler.printWithColor("Name: ", AnsiColor.YELLOW)
    val name = readlnOrNull() ?: "none"
    ConsoleHandler.printWithColor("Description: ", AnsiColor.YELLOW)
    val description = readlnOrNull() ?: "none"
    return arrayOf(name,description)

}

fun main() {
    val taskHandler = TaskHandler()

    //Btw, my fav color. Not related but I wanted to talk about it.
    ConsoleHandler.printlnWithColor("WELCOME TO THE SIMPLEST CONSOLE TASK MANAGER", AnsiColor.PURPLE).lineBreak()


    while (true) {
        val tasks = taskHandler.getTasks()
        if (tasks.isEmpty()) {
            ConsoleHandler.printlnWithColor("Currently no tasks have been added yet", AnsiColor.YELLOW).lineBreak()
        } else {
            printTasks(tasks)
        }

        ConsoleHandler.printlnWithColor("[1] Add task", AnsiColor.BLUE)
            .printlnWithColor("[2] Edit task", AnsiColor.BLUE)
            .printlnWithColor("[3] (Un)Complete task", AnsiColor.BLUE)
            .printWithColor("Select an option: ", AnsiColor.CYAN)
        val option = readln().toIntOrNull() ?: 1

        ConsoleHandler.clearConsole()
        printTasks(tasks)

        when (option) {
            1 -> {
                val info = printAsks()
                taskHandler.addTask(info[0], info[1])
            }

            2 -> {
                ConsoleHandler.printWithColor("Id: ", AnsiColor.YELLOW)
                val id = readln().toIntOrNull() ?: -1
                val info = printAsks()
                taskHandler.modifyTask(id, info[0], info[1])
            }

            3 -> {
                ConsoleHandler.printWithColor("Id: ", AnsiColor.YELLOW)
                val id = readln().toIntOrNull() ?: -1
                taskHandler.alternateTaskState(id)
            }
        }
        taskHandler.saveChanges()
        ConsoleHandler.clearConsole()
    }
}