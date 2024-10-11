package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.model.CodeExecutionResult
import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import org.springframework.stereotype.Component

@Component
class CodeExecutionUseCase(
    private val generateInputFile: GenerateInputFileUseCase,
    private val createNecessaryTestcaseFiles: CreateNecessaryTestcaseFilesUseCase,
    private val createDockerFile: CreateDockerFileUseCase,
    private val buildDockerImage: BuildDockerImageUseCase,
    private val executeForResults: ExecuteForResultsUseCase,
    private val deleteDockerImage: DeleteDockerImageUseCase,
    private val deleteDockerFile: DeleteDockerFileUseCase
) {
    operator fun invoke(
        userId: String,
        problemId: Int,
        userCode: String,
        language: ProgrammingLanguage,
        testcases: List<ProblemTestcase>
    ): List<CodeExecutionResult> {

        val (inputFilePath, fileName) = generateInputFile(userId, problemId, userCode, language)

        createNecessaryTestcaseFiles(inputFilePath, testcases)

        createDockerFile(language, inputFilePath, fileName)

        val imageName = buildDockerImage(userId, inputFilePath)

        val results = executeForResults(language, imageName, testcases)

        deleteDockerImage(imageName)

        deleteDockerFile(inputFilePath)

        return results.toList().filter { it !is CodeExecutionResult.Temporary }
    }
}