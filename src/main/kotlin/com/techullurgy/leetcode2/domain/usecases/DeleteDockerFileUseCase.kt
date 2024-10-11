package com.techullurgy.leetcode2.domain.usecases

import com.techullurgy.leetcode2.domain.services.FileService
import org.springframework.stereotype.Component

@Component
class DeleteDockerFileUseCase {
    operator fun invoke(filePath: String) {
        FileService.deleteFile("$filePath/Dockerfile")
    }
}