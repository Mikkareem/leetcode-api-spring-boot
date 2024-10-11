package com.techullurgy.leetcode2.network.models

import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import java.time.LocalDateTime

data class SubmissionDTO(
    val id: Int = 0,
    val language: ProgrammingLanguage,
    val code: String,
    val result: CodeSubmissionResult,
    val time: LocalDateTime,
    val problem: Int,
    val user: LeetcodeUser
)
