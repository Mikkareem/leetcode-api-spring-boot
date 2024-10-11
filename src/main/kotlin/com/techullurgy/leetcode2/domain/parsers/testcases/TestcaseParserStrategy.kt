package com.techullurgy.leetcode2.domain.parsers.testcases

import com.techullurgy.leetcode2.domain.model.ProblemTestcase

abstract class TestcaseParserStrategy {
    abstract fun parse(testcase: ProblemTestcase): String

    protected fun isSingleType(mask: Long): Boolean = (mask and TestcaseType.SINGLE_TYPE) != 0L
    protected fun isStringType(mask: Long): Boolean = (mask and TestcaseType.STRING_TYPE) != 0L
    protected fun is1DList(mask: Long): Boolean = (mask and TestcaseType.LIST_TYPE) != 0L
    protected fun is2DList(mask: Long): Boolean = (mask and TestcaseType.LIST_LIST_TYPE) != 0L
}