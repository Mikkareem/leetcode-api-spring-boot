package com.techullurgy.leetcode2.domain.parsers.testcases

import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.parsers.utils.RegexPattern
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class DefaultTestcaseParserStrategy : TestcaseParserStrategy() {
    override fun parse(testcase: ProblemTestcase): String {
        val builder = StringBuilder()

        for(index in testcase.input.indices) {
            val currentInput = testcase.input[index]
            val currentMask = testcase.masks[index]

            if(is2DList(currentMask)) {
                if(isNonStringType(currentMask)) {
                    val arrays = RegexPattern.ArraySingleRegex.findAll(currentInput).toList().map {
                        it.value.removePrefix("[").removeSuffix("]")
                    }
                    builder.appendWithNewLine(arrays.count())
                    arrays.forEach {
                        val groups = RegexPattern.SingleRegex.findAll(it).toList().map { it.value }
                        builder.appendWithNewLine(groups.count())
                        if(groups.count() > 0) {
                            builder.appendWithNewLine(groups.joinToString("\n"))
                        }
                    }
                } else if(isStringType(currentMask)) {
                    val arrays = RegexPattern.ArrayStringRegex.findAll(currentInput).toList().map {
                        it.value.removePrefix("[").removeSuffix("]")
                    }
                    builder.appendWithNewLine(arrays.count())
                    arrays.forEach {
                        val groups = RegexPattern.StringRegex.findAll(it).toList().map { it.value.removeSurrounding("\"") }
                        builder.appendWithNewLine(groups.count())
                        if(groups.count() > 0) {
                            builder.appendWithNewLine(groups.joinToString("\n"))
                        }
                    }
                }
            } else if(is1DList(currentMask)) {
                if(isNonStringType(currentMask)) {
                    RegexPattern.ArraySingleRegex.findAll(currentInput).first().value.removePrefix("[").removeSuffix("]").also {
                        val groups = RegexPattern.SingleRegex.findAll(it).toList().map { it.value }
                        builder.appendWithNewLine(groups.count())
                        if(groups.count() > 0) {
                            builder.appendWithNewLine(groups.joinToString("\n"))
                        }
                    }
                } else if(isStringType(currentMask)) {
                    RegexPattern.ArrayStringRegex.findAll(currentInput).first().value.removePrefix("[").removeSuffix("]").also {
                        val groups = RegexPattern.StringRegex.findAll(it).toList().map { it.value.removeSurrounding("\"") }
                        builder.appendWithNewLine(groups.count())
                        if(groups.count() > 0) {
                            builder.appendWithNewLine(groups.joinToString("\n"))
                        }
                    }
                }
            } else if(isSingleType(currentMask)) {
                if(isNonStringType(currentMask)) {
                    RegexPattern.SingleRegex.findAll(currentInput).first().value.also {
                        builder.appendWithNewLine(it)
                    }
                } else if(isStringType(currentMask)) {
                    RegexPattern.StringRegex.findAll(currentInput).first().value.also {
                        builder.appendWithNewLine(it.removeSurrounding("\""))
                    }
                }
            }
        }

        return builder.toString()
    }

    private fun StringBuilder.appendWithNewLine(s: Any) = append("$s\n")
}