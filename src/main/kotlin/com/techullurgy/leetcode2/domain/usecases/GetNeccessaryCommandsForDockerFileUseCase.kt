package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import org.springframework.stereotype.Component

@Component
class GetNecessaryCommandsForDockerFileUseCase {
    operator fun invoke(language: ProgrammingLanguage, fileName: String): String {
        return when (language) {
            ProgrammingLanguage.C -> """
                RUN gcc -o DockerWorld $fileName
                CMD ./DockerWorld $${"testcase_no"}
            """.trimIndent()

            ProgrammingLanguage.Cpp -> """
                RUN gcc -o DockerWorld -lstdc++ $fileName
                CMD ./DockerWorld $${"testcase_no"}
            """.trimIndent()

            ProgrammingLanguage.Java -> """
                RUN javac $fileName
                CMD java ${fileName.substringBefore('.')} $${"testcase_no"}
            """.trimIndent()

            ProgrammingLanguage.Python -> """
                CMD python3 $fileName $${"testcase_no"}
            """.trimIndent()

            ProgrammingLanguage.Javascript -> """
                CMD node $fileName $${"testcase_no"}
            """.trimIndent()
        }
    }
}