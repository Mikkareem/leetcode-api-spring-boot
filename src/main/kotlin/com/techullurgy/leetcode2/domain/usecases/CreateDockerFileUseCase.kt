package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.services.Compiler
import com.techullurgy.leetcode2.domain.services.FileService
import org.springframework.stereotype.Component

@Component
class CreateDockerFileUseCase(
    private val getNecessaryCommands: GetNecessaryCommandsForDockerFileUseCase
) {
    operator fun invoke(language: ProgrammingLanguage, inputFilePath: String, fileName: String) {
        val compiler = when (language) {
            ProgrammingLanguage.C -> Compiler.FROM_DOCKER_IMAGE_FOR_C_COMPILER
            ProgrammingLanguage.Cpp -> Compiler.FROM_DOCKER_IMAGE_FOR_CPP_COMPILER
            ProgrammingLanguage.Java -> Compiler.FROM_DOCKER_IMAGE_FOR_JAVA_COMPILER
            ProgrammingLanguage.Python -> Compiler.FROM_DOCKER_IMAGE_FOR_PYTHON3_COMPILER
            ProgrammingLanguage.Javascript -> Compiler.FROM_DOCKER_IMAGE_FOR_JAVASCRIPT_COMPILER
        }

        val dockerFileContent = """
            FROM $compiler
            ADD ./$fileName /tmp/${language.usage.lowercase()}/$fileName
            COPY ./testcases /tmp/${language.usage.lowercase()}/testcases
            ENV testcase_no=0
            WORKDIR /tmp/${language.usage.lowercase()}/
            CMD mkdir outputs
            ${getNecessaryCommands(language, fileName)}
        """.trimIndent()

        FileService.writeFile(filePath = "$inputFilePath/Dockerfile", value = dockerFileContent)
    }
}