package io.github.xkaih

import kotlinx.serialization.Serializable

@Serializable
data class TaskStorage(val nextId: Int, val tasks: Map<Int, Task>)
