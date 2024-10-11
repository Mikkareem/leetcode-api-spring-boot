package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.services.Compiler
import com.techullurgy.leetcode2.domain.services.FileService
import org.springframework.stereotype.Component

@Component
class GenerateInputFileUseCase(
    private val problemsRepository: ProblemsRepository,
) {
    operator fun invoke(
        userId: String,
        problemId: Int,
        userCode: String,
        language: ProgrammingLanguage
    ): Pair<String, String> {
        val problem = problemsRepository.findById(problemId).get()

        val replaceableString = when(language) {
            ProgrammingLanguage.C -> problem.fileContent.cReplaceStr
            ProgrammingLanguage.Cpp -> problem.fileContent.cppReplaceStr
            ProgrammingLanguage.Java -> problem.fileContent.javaReplaceStr
            ProgrammingLanguage.Python -> problem.fileContent.pythonReplaceStr
            ProgrammingLanguage.Javascript -> problem.fileContent.javascriptReplaceStr
        }

        val fileContent = when(language) {
            ProgrammingLanguage.C -> problem.fileContent.c
            ProgrammingLanguage.Cpp -> problem.fileContent.cpp
            ProgrammingLanguage.Java -> problem.fileContent.java
            ProgrammingLanguage.Python -> problem.fileContent.python
            ProgrammingLanguage.Javascript -> problem.fileContent.javascript
        }

        val newFileContent = fileContent.replace(replaceableString, userCode)

        val inputFilePath = "temp/${language.usage.lowercase()}/$userId"

        val fileName = when(language) {
            ProgrammingLanguage.C -> Compiler.C_INPUT_FILE_NAME
            ProgrammingLanguage.Cpp -> Compiler.CPP_INPUT_FILE_NAME
            ProgrammingLanguage.Java -> Compiler.JAVA_INPUT_FILE_NAME
            ProgrammingLanguage.Python -> Compiler.PYTHON_INPUT_FILE_NAME
            ProgrammingLanguage.Javascript -> Compiler.JAVASCRIPT_INPUT_FILE_NAME
        }

        FileService.writeFile("$inputFilePath/$fileName", newFileContent)

        return inputFilePath to fileName
    }
}