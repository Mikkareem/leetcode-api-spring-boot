package com.techullurgy.leetcode2.domain.model

data class TestcaseInputDetails(
    val name: String,
    val type: TestcaseInputType,
    val collectionType: TestcaseInputCollectionType,
    val displayOrder: Int
)
