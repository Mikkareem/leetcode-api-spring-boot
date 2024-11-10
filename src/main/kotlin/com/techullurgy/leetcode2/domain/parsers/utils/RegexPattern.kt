package com.techullurgy.leetcode2.domain.parsers.utils

object RegexPattern {
    val ArraySingleRegex = Regex("\\[(?:-?\\d+(?:,\\s*-?\\d+)*)*]|\\[,?\\s*'.'(?:,\\s*'.')*]")
    val ArrayStringRegex = Regex("\\[\\s*\"[^\"]*\"(?:,\\s*\"[^\"]*\")*]|\\[]")

    val SingleRegex = Regex("-?\\d+|'.'")
    val StringRegex = Regex("\"[^\"]*\"")
}