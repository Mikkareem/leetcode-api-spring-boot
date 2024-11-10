package com.techullurgy.leetcode2

import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.parsers.testcases.DefaultTestcaseParserStrategy
import com.techullurgy.leetcode2.domain.parsers.testcases.TestcaseParserStrategy
import com.techullurgy.leetcode2.domain.parsers.testcases.TestcaseType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals


class TestcaseParserTest {
    private lateinit var defaultParser: TestcaseParserStrategy

    @BeforeEach
    fun beforeEach() {
        defaultParser = DefaultTestcaseParserStrategy()
    }

    @Test
    fun singleStringTest() {
        val testcase = ProblemTestcase(
            id = 0,
            input = listOf("\"abcd\""),
            masks = listOf(
                TestcaseType.STRING_TYPE or TestcaseType.SINGLE_TYPE
            )
        )

        val result = defaultParser.parse(testcase)
        assertEquals("abcd\n", result)
    }

    @Test
    fun singleNonStringTest() {
        val testcase = ProblemTestcase(
            id = 0,
            input = listOf("234"),
            masks = listOf(
                TestcaseType.NON_STRING_TYPE or TestcaseType.SINGLE_TYPE
            )
        )

        val result = defaultParser.parse(testcase)
        assertEquals("234\n", result)
    }

    @Test
    fun `2DStringTest`() {
        val testcase = ProblemTestcase(
            id = 0,
            input = listOf("""[["abc", "cde", "", "We are guys"], [], ["Welcome ", "to", " India"], [""], []]"""),
            masks = listOf(
                TestcaseType.STRING_TYPE or TestcaseType.LIST_LIST_TYPE
            )
        )

        val result = defaultParser.parse(testcase)
        assertEquals("""
            5
            4
            abc
            cde
            
            We are guys
            0
            3
            Welcome 
            to
             India
            1
            
            0
            
        """.trimIndent(), result)
    }

    @Test
    fun `2DNonStringTest`() {
        val testcase = ProblemTestcase(
            id = 0,
            input = listOf("[[234, -45, 27], [45, 991, 172], [], [], [1]]"),
            masks = listOf(
                TestcaseType.NON_STRING_TYPE or TestcaseType.LIST_LIST_TYPE
            )
        )

        val result = defaultParser.parse(testcase)
        assertEquals("5\n3\n234 -45 27\n3\n45 991 172\n0\n\n0\n\n1\n1\n", result)
    }

    @Test
    fun `1DStringTest1`() {
        val testcase = ProblemTestcase(
            id = 0,
            input = listOf("""["abcd", "bcd", "", "abckasjjkasd", ""]"""),
            masks = listOf(
                TestcaseType.STRING_TYPE or TestcaseType.LIST_TYPE
            )
        )

        val result = defaultParser.parse(testcase)
        assertEquals("""
            5
            "abcd" "bcd" "" "abckasjjkasd" ""
            
        """.trimIndent(), result)
    }

    @Test
    fun `1DStringTest2`() {
        val testcase = ProblemTestcase(
            id = 0,
            input = listOf("""["abcd", "We are welcoming", "", "abckasjjkasd", ""]"""),
            masks = listOf(
                TestcaseType.STRING_TYPE or TestcaseType.LIST_TYPE
            )
        )

        val result = defaultParser.parse(testcase)
        assertEquals("""
            5
            "abcd" "We are welcoming" "" "abckasjjkasd" ""
            
            """.trimIndent(), result)
    }

    @Test
    fun `1DNonStringTest`() {
        val testcase = ProblemTestcase(
            id = 0,
            input = listOf("[234, 23, -89, -66, 89]"),
            masks = listOf(
                TestcaseType.NON_STRING_TYPE or TestcaseType.LIST_TYPE
            )
        )

        val result = defaultParser.parse(testcase)
        assertEquals("5\n234\n23\n-89\n-66\n89\n", result)
    }
}