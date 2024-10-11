package com.techullurgy.leetcode2.network.models

import com.techullurgy.leetcode2.domain.model.Difficulty

data class ProblemListItem(
    val problemNo: Int,
    val problemName: String,
    val problemDifficulty: Difficulty,
    val isSolved: Boolean = false
)
