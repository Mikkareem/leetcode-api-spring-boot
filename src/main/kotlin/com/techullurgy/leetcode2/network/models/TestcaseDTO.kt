package com.techullurgy.leetcode2.network.models

data class TestcaseDTO(
    val testcaseNo: Int,
    val testcase: List<TestcaseInputDTO>
)

data class TestcaseInputDTO(
    val name: String,
    val value: String,
    val isString: Boolean = false,
    val collectionType: String = "SINGLE"
)