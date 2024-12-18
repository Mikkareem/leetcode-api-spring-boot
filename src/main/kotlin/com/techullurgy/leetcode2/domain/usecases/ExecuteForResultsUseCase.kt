package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.model.CodeExecutionResult
import com.techullurgy.leetcode2.domain.model.CodeExecutionType
import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import org.springframework.stereotype.Component

@Component
class ExecuteForResultsUseCase(
    private val executeDockerImage: ExecuteDockerImageUseCase
) {
    operator fun invoke(
        language: ProgrammingLanguage,
        imageName: String,
        testcases: List<ProblemTestcase>,
        executionType: CodeExecutionType
    ): Array<CodeExecutionResult> {
        val results = Array<CodeExecutionResult>(testcases.size) { CodeExecutionResult.NotExecuted }

        for (index in testcases.indices) {
            val result = executeDockerImage(language, imageName, index + 1)

            if (result.first) {
                results[index] = CodeExecutionResult.Accepted
            } else {
                if (result.second == CodeExecutionResult.TimeLimitExceeded.name) {
                    results[index] = CodeExecutionResult.TimeLimitExceeded
                    break
                } else if (result.second == "") {
                    results[index] = CodeExecutionResult.WrongAnswer
                } else {
                    results[index] = CodeExecutionResult.RuntimeError(result.second)
                    break
                }
                if(executionType == CodeExecutionType.SUBMIT) {
                    break
                }
            }
        }

        return results
    }
}