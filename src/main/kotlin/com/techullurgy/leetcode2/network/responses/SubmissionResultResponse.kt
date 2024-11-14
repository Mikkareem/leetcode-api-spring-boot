package com.techullurgy.leetcode2.network.responses

import com.techullurgy.leetcode2.data.entities.FailedTestcase
import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import java.time.LocalDateTime

sealed class SubmissionResultResponse {
    data class AcceptedSubmissionResponse(
        val submissionId: Long,
        val problemId: Long,
        val userId: Long,
        val submittedCode: String,
        val codeLanguage: ProgrammingLanguage,
        val submissionTime: LocalDateTime,
        val executionTime: Long,
        val memoryConsumption: Long,
        val verdict: CodeSubmissionResult = CodeSubmissionResult.Accepted
    ): SubmissionResultResponse()

    data class NonAcceptedSubmissionResponse(
        val submissionId: Long,
        val problemId: Long,
        val userId: Long,
        val submittedCode: String,
        val codeLanguage: ProgrammingLanguage,
        val submissionTime: LocalDateTime,
        val verdict: CodeSubmissionResult,
        val totalTestcasesCount: Int,
        val executedTestcasesCount: Int,
        val failedTestcase: FailedTestcase
    ): SubmissionResultResponse()
}