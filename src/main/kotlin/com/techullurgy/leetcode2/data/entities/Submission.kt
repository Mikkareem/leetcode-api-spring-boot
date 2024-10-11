package com.techullurgy.leetcode2.data.entities

import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Submission(
    @Id
    @GeneratedValue
    val id: Int = 0,
    val language: ProgrammingLanguage,
    val code: String,
    val result: CodeSubmissionResult,
    val time: LocalDateTime,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "problem_id")
    val problem: Problem,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    val user: LeetcodeUser
)