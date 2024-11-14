package com.techullurgy.leetcode2.domain.model

enum class CodeSubmissionResult {
    CompilationError, TimeLimitExceeded, WrongAnswer, Accepted, RuntimeError, NotExecuted;

    fun isResultExists() = this == Accepted || this == WrongAnswer
}