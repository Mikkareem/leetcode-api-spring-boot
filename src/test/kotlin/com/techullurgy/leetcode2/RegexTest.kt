package com.techullurgy.leetcode2

import com.techullurgy.leetcode2.domain.parsers.utils.RegexPattern
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RegexTest {
    @Test
    fun `2DRegexSingleTest`() {
        val arrayOfInt = "[[1,2,3,4], [5,6,-7], [], [8,9,10]]"
        val matches1 = RegexPattern.ArraySingleRegex.findAll(arrayOfInt).toList().map { it.value }
        println(matches1)
        assertEquals(4, matches1.count())

        val arrayOfChar = "[['a','b','c','.'], ['*', '(', '{'], [], ['R']]"
        val matches2 = RegexPattern.ArraySingleRegex.findAll(arrayOfChar).toList().map { it.value }
        println(matches2)
        assertEquals(4, matches2.count())
    }

    @Test
    fun `2DRegexStringTest`() {
        val arrayOfString = """[["abc", "cde", ""],[""],[],["", "k.,(){}[]"], ["We are welcoming person", "People are very heavy"]]"""
        val matches1 = RegexPattern.ArrayStringRegex.findAll(arrayOfString).toList().map { it.value }
        println(matches1)
        assertEquals(5, matches1.count())
    }

    @Test
    fun singleRegexTest() {
        assertEquals("-123", RegexPattern.SingleRegex.findAll("-123").first().value)
        assertEquals("' '", RegexPattern.SingleRegex.findAll("' '").first().value)
        assertEquals("'.'", RegexPattern.SingleRegex.findAll("'.'").first().value)
        assertEquals(true, RegexPattern.SingleRegex.findAll("a").toList().isEmpty())
    }

    @Test
    fun stringRegexTest() {
        val string = """"abcd""""
        val matches1 = RegexPattern.StringRegex.findAll(string).first().value
        println(matches1)
        assertEquals("\"abcd\"", matches1)

        val string2 = """"People are always welcome""""
        val matches2 = RegexPattern.StringRegex.findAll(string2).first().value
        println(matches2)
        assertEquals("\"People are always welcome\"", matches2)

        assertEquals("\"abcd\"", RegexPattern.StringRegex.findAll(""""abcd"""").first().value)
        assertEquals("\"People are always welcome\"", RegexPattern.StringRegex.findAll(""""People are always welcome"""").first().value)
        assertEquals("\"\"", RegexPattern.StringRegex.findAll("\"\"").first().value)
        assertEquals(true, RegexPattern.StringRegex.findAll("a").toList().isEmpty())
    }
}