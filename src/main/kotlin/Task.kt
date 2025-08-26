package io.github.xkaih

import kotlinx.serialization.*

//This is my first time using data classes. On a first moment I thought to do this with a class, but I realized that this exists
//Maybe later I could add something like expiration date but, for the moment this is good
@Serializable
data class Task(var name: String, var description: String, var completed: Boolean = false)
