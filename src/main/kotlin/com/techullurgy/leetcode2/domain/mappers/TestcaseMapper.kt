package com.techullurgy.leetcode2.domain.mappers

import com.techullurgy.leetcode2.data.entities.Testcase
import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.parsers.testcases.TestcaseType
import com.techullurgy.leetcode2.network.models.TestcaseDTO

fun TestcaseDTO.toProblemTestcase() = ProblemTestcase(
    id = testcaseNo,
    isHidden = false,
    input = testcase.map { it.value },
    masks = testcase.map {
        (if(it.isString) {
            TestcaseType.STRING_TYPE
        } else {
            TestcaseType.SINGLE_TYPE
        }) or (
            when(it.collectionType) {
                "LIST" -> TestcaseType.LIST_TYPE
                "LIST_LIST" -> TestcaseType.LIST_LIST_TYPE
                else -> 0L
            }
        )
    }
)

fun Testcase.toProblemTestcase() = ProblemTestcase(
    id = testcaseNo,
    isHidden = false,
    input = details.map { it.inputValue },
    masks = details.map { it.typeMask }
)