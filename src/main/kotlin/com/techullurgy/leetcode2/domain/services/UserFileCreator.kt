package com.techullurgy.leetcode2.domain.services

import java.io.Closeable
import java.io.File
import kotlin.io.deleteRecursively

class UserFileCreator(
    userId: String,
    language: String
): Closeable {
    val file = File("temp/$language/$userId").apply { mkdirs() }

    override fun close() {
        file.deleteRecursively()
    }
}