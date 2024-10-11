package com.techullurgy.leetcode2.network.responses

import com.techullurgy.leetcode2.network.models.ProblemListItem

data class ProblemsListResponse(
    val problems: List<ProblemListItem>
)
