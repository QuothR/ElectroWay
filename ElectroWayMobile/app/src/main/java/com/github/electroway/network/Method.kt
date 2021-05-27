package com.github.electroway.network

sealed class Method

object Get : Method()
data class Post(val body: String) : Method()
data class Put(val body: String) : Method()
object Delete : Method()