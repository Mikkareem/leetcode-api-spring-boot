package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.utils.debug
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class DeleteDockerImageUseCase {
    operator fun invoke(imageName: String) {
        val builder = ProcessBuilder("docker", "rmi", "$imageName:latest")
        val process = builder.start();
        val isNotAborted = process.waitFor(10, TimeUnit.SECONDS)

        if (isNotAborted) {
            if (process.exitValue() == 0) {
                println("$imageName successfully deleted from docker registry")
            } else {
                process.debug()
            }
        }
    }
}