package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.model.CodeExecutionResult
import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult
import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.model.TestcaseResult
import com.techullurgy.leetcode2.domain.services.FileService
import org.springframework.stereotype.Component
import java.io.File

@Component
class GenerateTestcaseResultsUseCase {
    operator fun invoke(userFolder: File, testcases: List<ProblemTestcase>, results: List<CodeExecutionResult>): List<TestcaseResult> {
        return results.mapIndexed { index, it ->
            val currentTestcase = testcases[index]

            val result = when(it) {
                CodeExecutionResult.Accepted -> CodeSubmissionResult.Accepted
                is CodeExecutionResult.CompilationError -> CodeSubmissionResult.CompilationError
                is CodeExecutionResult.RuntimeError -> CodeSubmissionResult.RuntimeError
                CodeExecutionResult.TimeLimitExceeded -> CodeSubmissionResult.TimeLimitExceeded
                CodeExecutionResult.WrongAnswer -> CodeSubmissionResult.WrongAnswer
                else -> CodeSubmissionResult.NotExecuted
            }

            val expectedResult = if(result.isResultExists()) {
                FileService.getContentFromFile("${userFolder.canonicalPath.removeSuffix("/")}/outputs/eResult${currentTestcase.id}.txt")
            } else ""
            val yourResult = if(result.isResultExists()) {
                FileService.getContentFromFile("${userFolder.canonicalPath.removeSuffix("/")}/outputs/result${currentTestcase.id}.txt")
            } else ""
            val stdout = if(result.isResultExists()) {
                FileService.getContentFromFile("${userFolder.canonicalPath.removeSuffix("/")}/outputs/output${currentTestcase.id}.txt")
            } else ""

            TestcaseResult(
                testcase = currentTestcase,
                expectedResult = expectedResult,
                yourResult = yourResult,
                stdout = stdout,
                result = result
            )
        }
    }
}