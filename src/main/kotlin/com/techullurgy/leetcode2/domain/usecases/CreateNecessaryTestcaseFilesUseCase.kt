package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.parsers.testcases.TestcaseParserStrategy
import com.techullurgy.leetcode2.domain.services.FileService
import org.springframework.stereotype.Component
import java.io.File

@Component
class CreateNecessaryTestcaseFilesUseCase(
    private val testcaseParser: TestcaseParserStrategy
) {
    operator fun invoke(inputFilePath: String, testcases: List<ProblemTestcase>) {
        File("$inputFilePath/outputs").mkdir()
        testcases.forEach { it ->
            val testcaseFilePath = "$inputFilePath/testcases/input${it.id}.txt"
            FileService.writeFile(testcaseFilePath, testcaseParser.parse(it))
        }
    }
}