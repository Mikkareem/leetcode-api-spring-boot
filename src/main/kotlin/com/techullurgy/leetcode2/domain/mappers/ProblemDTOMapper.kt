package com.techullurgy.leetcode2.domain.mappers

import com.techullurgy.leetcode2.data.entities.Problem
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.network.models.ProblemDTO
import com.techullurgy.leetcode2.network.models.TestcaseInputModel
import com.techullurgy.leetcode2.network.models.TestcaseModel

fun Problem.toProblemDTO(language: ProgrammingLanguage) = ProblemDTO(
    problemNo = id,
    title = title,
    description = description,
    difficulty = difficulty,
    preferredLanguage = language,
    preferredSnippet = when(language) {
        ProgrammingLanguage.C -> snippet.c
        ProgrammingLanguage.Cpp -> snippet.cpp
        ProgrammingLanguage.Java -> snippet.java
        ProgrammingLanguage.Python -> snippet.python
        ProgrammingLanguage.Javascript -> snippet.javascript
    },
    sampleTestcases = testcases.filter { !it.isHidden }.mapIndexed { index, it ->
        TestcaseModel(
            id = it.testcaseId,
            isHidden = it.isHidden,
            inputs = it.inputs.mapIndexed { index, it ->
                TestcaseInputModel(
                    value = it.value,
                    details = it.format.toTestcaseInputDetails()
                )
            }
        )
    }
)