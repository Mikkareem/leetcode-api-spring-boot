package com.techullurgy.leetcode2.network.models

import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult

data class TestcaseResultDTO(
    val testcase: TestcaseModel,
    val expectedResult: String,
    val yourResult: String,
    val stdout: String,
    val result: CodeSubmissionResult
)
