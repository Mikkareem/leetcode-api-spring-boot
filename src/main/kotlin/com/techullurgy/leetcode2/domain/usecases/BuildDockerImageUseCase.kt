package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.utils.debug
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class BuildDockerImageUseCase {
    operator fun invoke(userId: String, filePath: String): Pair<Boolean, String> {
        val imageName = "my-image-${userId}"
        val builder = ProcessBuilder("docker", "build", "-t", imageName, filePath)
        val process = builder.start()
        val isNotAborted = process.waitFor(10, TimeUnit.SECONDS)

        return if (isNotAborted) {
            if (process.exitValue() == 0) {
                println("$imageName Docker image created successfully")
                true to imageName
            } else {
                process.debug()
                false to imageName
            }
        } else {
            process.debug()
            false to imageName
        }
    }
}