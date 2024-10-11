package com.techullurgy.leetcode2.network.responses

import com.techullurgy.leetcode2.network.models.TestcaseResultDTO

data class RunResultResponse(
    val problemId: Int,
    val results: List<TestcaseResultDTO>
)
