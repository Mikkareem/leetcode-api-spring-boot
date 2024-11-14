package com.techullurgy.leetcode2.data.entities

import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@Entity
data class Submission(
    @Id
    @GeneratedValue
    val id: Int = 0,
    val language: ProgrammingLanguage,
    val code: String,
    val verdict: CodeSubmissionResult,
    val time: LocalDateTime,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    val problem: Problem,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: LeetcodeUser,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "accepted_props_id", nullable = true)
    val acceptedProps: AcceptedProps? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "wrong_props_id", nullable = true)
    val wrongAnswerProps: WrongAnswerProps? = null
)

@Entity
data class AcceptedProps(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val executionTime: Long,
    val memoryConsumption: Long
)

@Entity
data class WrongAnswerProps(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val totalTestcases: Int,
    val executedTestcases: Int,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "failed_testcase_id")
    val failedTestcase: FailedTestcase
)

@Entity
data class FailedTestcase(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val expectedOutput: String,
    val yourOutput: String,
    val stdout: String,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "failed_testcase_id")
    val inputs: List<FailedTestcaseInput>
)

@Entity
data class FailedTestcaseInput(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val name: String,
    val input: String
)