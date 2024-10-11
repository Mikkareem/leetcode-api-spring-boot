package com.techullurgy.leetcode2.domain.usecases

import java.util.concurrent.TimeUnit
import com.techullurgy.leetcode2.domain.services.Compiler
import com.techullurgy.leetcode2.domain.model.CodeExecutionResult
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.utils.getErrorData
import org.springframework.stereotype.Component

@Component
class ExecuteDockerImageUseCase {
    operator fun invoke(language: ProgrammingLanguage, imageName: String, testcaseNo: Int): Pair<Boolean, String> {

        val builder = ProcessBuilder(
            "docker",
            "run",
            "--rm",
            "-v",
            "${Compiler.CURRENT_WORKING_DIRECTORY_OF_HOST}/temp/${language.usage.lowercase()}/${imageName.replace("my-image-", "")}/outputs:/tmp/${language.usage.lowercase()}/outputs",
            "--name",
            "$imageName-container",
            "-e",
            "testcase_no=$testcaseNo",
            imageName
        )
        val process = builder.start()
        val isNotAborted = process.waitFor(5, TimeUnit.SECONDS)

        if (isNotAborted) {
            if (process.exitValue() == Compiler.ACCEPTED_PROCESS_EXIT_CODE) {
                return Pair(true, "")
            } else if(process.exitValue() == Compiler.WRONG_ANSWER_PROCESS_EXIT_CODE) {
                return Pair(false, "")
            } else if(process.exitValue() == Compiler.TIME_LIMIT_EXCEEDED_PROCESS_EXIT_CODE) {
                return Pair(false, CodeExecutionResult.TimeLimitExceeded.name)
            } else {
                val error = process.getErrorData()
                return Pair(false, error)
            }
        } else {
            forceStopAndDeleteContainer(imageName)
            process.destroy()
            process.waitFor()
        }

        return Pair(false, CodeExecutionResult.TimeLimitExceeded.name)
    }

    private fun forceStopAndDeleteContainer(imageName: String) {
        val builder = ProcessBuilder("docker", "stop", "$imageName-container")
        val process = builder.start()
        process.waitFor()
    }
}