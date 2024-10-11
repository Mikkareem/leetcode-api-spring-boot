package com.techullurgy.leetcode2.domain.mappers

import com.techullurgy.leetcode2.data.entities.Problem
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.network.models.ProblemDTO
import com.techullurgy.leetcode2.network.models.TestcaseDTO
import com.techullurgy.leetcode2.network.models.TestcaseInputDTO

fun Problem.toProblemDTO(language: ProgrammingLanguage) = ProblemDTO(
    problemNo = problemNo,
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
    sampleTestcases = testcases.filter { !it.isHidden }.map {
        TestcaseDTO(
            testcaseNo = it.testcaseNo,
            testcase = it.details.map {
                TestcaseInputDTO(
                    name = it.inputName,
                    value = it.inputValue
                )
            }
        )
    }
)