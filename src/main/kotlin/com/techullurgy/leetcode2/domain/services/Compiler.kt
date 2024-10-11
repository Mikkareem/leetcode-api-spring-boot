package com.techullurgy.leetcode2.domain.services

object Compiler {
    const val FROM_DOCKER_IMAGE_FOR_C_COMPILER = "gcc:9.5.0"
    const val FROM_DOCKER_IMAGE_FOR_JAVA_COMPILER = "amazoncorretto:11"
    const val FROM_DOCKER_IMAGE_FOR_JAVASCRIPT_COMPILER = "node:21"
    const val FROM_DOCKER_IMAGE_FOR_CPP_COMPILER = "gcc:9.5.0"
    const val FROM_DOCKER_IMAGE_FOR_PYTHON3_COMPILER = "gcc:9.5.0"

    const val TIME_LIMIT_EXCEEDED_PROCESS_EXIT_CODE = 172
    const val WRONG_ANSWER_PROCESS_EXIT_CODE = 168
    const val ACCEPTED_PROCESS_EXIT_CODE = 0

    const val C_INPUT_FILE_NAME = "Main.c"
    const val CPP_INPUT_FILE_NAME = "Main.cpp"
    const val JAVA_INPUT_FILE_NAME = "Main.java"
    const val PYTHON_INPUT_FILE_NAME = "Main.py"
    const val JAVASCRIPT_INPUT_FILE_NAME = "Main.js"

    val CURRENT_WORKING_DIRECTORY_OF_HOST = System.getProperty("user.dir")
}