package com.techullurgy.leetcode2.domain.services

import com.techullurgy.leetcode2.domain.model.CodeExecutionType
import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.model.TestcaseResult
import com.techullurgy.leetcode2.domain.usecases.CodeExecutionUseCase
import com.techullurgy.leetcode2.domain.usecases.GenerateTestcaseResultsUseCase
import org.springframework.stereotype.Service

@Service
class CodeExecutionService(
    private val executeCode: CodeExecutionUseCase,
    private val generateResults: GenerateTestcaseResultsUseCase
) {
    fun executeFor(
        userId: String,
        problemId: Int,
        userCode: String,
        language: ProgrammingLanguage,
        testcases: List<ProblemTestcase>,
        executionType: CodeExecutionType
    ): List<TestcaseResult> {
        return UserFileCreator(userId, language.usage).use {
            val results = executeCode(userId, problemId, userCode, language, testcases, executionType)
            generateResults(it.file, testcases, results)
        }
    }

    fun print() {
        println("Printed Hello world")
    }
}