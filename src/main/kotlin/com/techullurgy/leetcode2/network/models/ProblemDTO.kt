package com.techullurgy.leetcode2.network.models

import com.techullurgy.leetcode2.domain.model.Difficulty
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage

data class ProblemDTO(
    val problemNo: Int,
    val title: String,
    val difficulty: Difficulty,
    val description: String,
    val preferredSnippet: String,
    val preferredLanguage: ProgrammingLanguage,
    val sampleTestcases: List<TestcaseModel>
)