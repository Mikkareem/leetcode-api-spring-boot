package com.techullurgy.leetcode2.domain.model

data class ProblemTestcase(
    val id: Int,
    val isHidden: Boolean = true,
    val input: List<String>,
    val masks: List<Long>
)