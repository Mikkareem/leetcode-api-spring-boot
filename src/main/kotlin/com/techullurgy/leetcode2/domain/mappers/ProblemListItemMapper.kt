package com.techullurgy.leetcode2.domain.mappers

import com.techullurgy.leetcode2.data.entities.Problem
import com.techullurgy.leetcode2.network.models.ProblemListItem

fun Problem.toProblemListItem() = ProblemListItem(
    problemNo = problemNo,
    problemName = title,
    problemDifficulty = difficulty
)