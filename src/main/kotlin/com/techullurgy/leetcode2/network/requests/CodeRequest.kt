package com.techullurgy.leetcode2.network.requests

import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.network.models.TestcaseDTO

data class CodeRequest(
    val problemId: Int,
    val language: ProgrammingLanguage,
    val userCode: String,
    val sampleTestcases: List<TestcaseDTO>
)
