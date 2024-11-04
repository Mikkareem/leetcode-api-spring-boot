package com.techullurgy.leetcode2.network.models

import com.techullurgy.leetcode2.domain.model.TestcaseInputDetails

data class TestcaseModel(
    val id: Long = 0,
    val inputs: List<TestcaseInputModel>,
    val isHidden: Boolean
)

data class TestcaseInputModel(
    val value: String,
    val details: TestcaseInputDetails
)