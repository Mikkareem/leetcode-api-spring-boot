package com.techullurgy.leetcode2.domain.model

sealed interface CodeExecutionResult {
    data object Temporary : CodeExecutionResult
    data class CompilationError(val error: String): CodeExecutionResult
    data object TimeLimitExceeded : CodeExecutionResult { val name = "Time Limit Exceeded" }
    data object Accepted : CodeExecutionResult
    data object WrongAnswer: CodeExecutionResult
    data class RuntimeError(val error: String) : CodeExecutionResult
}