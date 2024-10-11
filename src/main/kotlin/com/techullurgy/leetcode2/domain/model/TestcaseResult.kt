package com.techullurgy.leetcode2.domain.model

data class TestcaseResult(
    val testcase: ProblemTestcase,
    val expectedResult: String,
    val yourResult: String,
    val stdout: String,
    val result: CodeSubmissionResult
)
