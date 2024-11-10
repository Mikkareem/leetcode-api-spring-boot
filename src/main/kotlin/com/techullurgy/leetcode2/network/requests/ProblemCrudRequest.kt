package com.techullurgy.leetcode2.network.requests

import com.techullurgy.leetcode2.data.entities.FileContent
import com.techullurgy.leetcode2.data.entities.Snippet
import com.techullurgy.leetcode2.domain.model.Difficulty
import com.techullurgy.leetcode2.domain.model.TestcaseInputDetails

data class ProblemCrudRequest(
    val id: Int = 0,
    val title: String,
    val description: String,
    val difficulty: Difficulty,
    val snippets: Snippet,
    val fileContent: FileContent,
    val testcaseFormats: List<TestcaseInputDetails>
)
