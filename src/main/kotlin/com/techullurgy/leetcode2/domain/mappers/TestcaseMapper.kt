package com.techullurgy.leetcode2.domain.mappers

import com.techullurgy.leetcode2.data.entities.Testcase
import com.techullurgy.leetcode2.data.entities.TestcaseFormat
import com.techullurgy.leetcode2.data.entities.TestcaseInput
import com.techullurgy.leetcode2.domain.mappers.toTestcase
import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.model.TestcaseInputCollectionType
import com.techullurgy.leetcode2.domain.model.TestcaseInputDetails
import com.techullurgy.leetcode2.domain.model.TestcaseInputType
import com.techullurgy.leetcode2.domain.parsers.testcases.TestcaseType
import com.techullurgy.leetcode2.network.models.TestcaseInputModel
import com.techullurgy.leetcode2.network.models.TestcaseModel

fun TestcaseInputDetails.toTestcaseFormat() = TestcaseFormat(
    name = name,
    displayOrder = displayOrder,
    typeMask = (
        when(type) {
            TestcaseInputType.STRING -> TestcaseType.STRING_TYPE
            TestcaseInputType.NON_STRING -> TestcaseType.NON_STRING_TYPE
        }
    ) or (
        when(collectionType) {
            TestcaseInputCollectionType.LIST -> TestcaseType.LIST_TYPE
            TestcaseInputCollectionType.LIST_LIST -> TestcaseType.LIST_LIST_TYPE
            TestcaseInputCollectionType.SINGLE -> TestcaseType.SINGLE_TYPE
        }
    )
)

fun TestcaseFormat.toTestcaseInputDetails() = TestcaseInputDetails(
    name = name,
    displayOrder = displayOrder,
    type = when {
        (typeMask and TestcaseType.STRING_TYPE) != 0L -> TestcaseInputType.STRING
        else -> TestcaseInputType.NON_STRING
    },
    collectionType = when {
        (typeMask and TestcaseType.LIST_TYPE) != 0L -> TestcaseInputCollectionType.LIST
        (typeMask and TestcaseType.LIST_LIST_TYPE) != 0L -> TestcaseInputCollectionType.LIST_LIST
        else -> TestcaseInputCollectionType.SINGLE
    }
)

fun TestcaseModel.toProblemTestcase() = ProblemTestcase(
    id = id,
    isHidden = isHidden,
    input = inputs.map { it.value },
    masks = inputs.map { it.details.toTestcaseFormat().typeMask }
)

fun Testcase.toProblemTestcase() = ProblemTestcase(
    id = testcaseId,
    isHidden = isHidden,
    input = inputs.map { it.value },
    masks = inputs.map { it.format.typeMask }
)

fun Testcase.toTestcaseModel() = TestcaseModel(
    id = testcaseId,
    isHidden = isHidden,
    inputs = inputs.map {
        TestcaseInputModel(
            value = it.value,
            details = it.format.toTestcaseInputDetails()
        )
    }
)

fun TestcaseModel.toTestcase() = Testcase(
    testcaseId = id,
    isHidden = isHidden,
).apply {
    val newDetails = this@toTestcase.inputs.map {
        TestcaseInput(
            value = it.value,
            format = it.details.toTestcaseFormat()
        )
    }
    inputs.clear()
    inputs.addAll(newDetails)
}