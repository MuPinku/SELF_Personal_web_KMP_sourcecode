package main.unified

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform