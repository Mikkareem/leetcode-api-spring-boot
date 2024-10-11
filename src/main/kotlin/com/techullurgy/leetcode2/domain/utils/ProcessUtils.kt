package com.techullurgy.leetcode2.domain.utils

import java.io.BufferedReader
import java.io.InputStreamReader

fun Process.getOutputData(): String {
    val oStream = BufferedReader(InputStreamReader(inputStream))
    val oBuilder = StringBuilder()
    var line: String?
    while (oStream.readLine().also { line = it } != null) {
        oBuilder.append(line)
        oBuilder.append(System.getProperty("line.separator"))
    }
    return oBuilder.toString()
}

fun Process.getErrorData(): String {
    val eStream = BufferedReader(InputStreamReader(errorStream))
    val eBuilder = StringBuilder()
    var line: String?
    while (eStream.readLine().also { line = it } != null) {
        eBuilder.append(line)
        eBuilder.append(System.getProperty("line.separator"))
    }
    return eBuilder.toString()
}

fun Process.debug() {
    val output = getOutputData()
    println("Output: \n$output")

    val error = getErrorData()
    println("Error: \n$error")
}