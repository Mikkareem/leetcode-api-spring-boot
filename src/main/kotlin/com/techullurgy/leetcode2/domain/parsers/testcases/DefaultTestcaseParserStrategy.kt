package com.techullurgy.leetcode2.domain.parsers.testcases

import com.techullurgy.leetcode2.domain.model.ProblemTestcase
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
                if(isStringType(currentMask)) {
                    val twoDArrayOfSingleRegex = Regex("""(\[(?:-?\d+(?:,\s*-?\d+)*)*])""")
                    builder.append(twoDArrayOfSingleRegex.findAll(currentInput).count()) // row
                    builder.append("\n")
                    builder.append(
                        twoDArrayOfSingleRegex.findAll(currentInput).map {
                            it.value.trim().removePrefix("[").removeSuffix("]").split(",").size
                        }.max()
                    ) // column
                    builder.append("\n")
                    builder.append(
                        twoDArrayOfSingleRegex.findAll(currentInput).map { it.value }.joinToString("\n") {
                            if(it.trim().equals("[]")) "$$$$$$$$$$" else it.trim().removePrefix("[").removeSuffix("]").split(",").joinToString(" ") { it.trim() }
                        }
                    )
                } else {
                    val twoDArrayOfStringRegex = Regex("""(\[(?:\s*,?\s*"[a-zA-Z0-9 .*%$#@!\-+=:;'()\[\]{}]*")*\s*])""")
                    builder.append(twoDArrayOfStringRegex.findAll(currentInput).count()) // row
                    builder.append("\n")
                    builder.append(
                        twoDArrayOfStringRegex.findAll(currentInput).map {
                            it.value.trim().removePrefix("[").removeSuffix("]").split(",").size
                        }.max()
                    ) // column
                    builder.append("\n")
                    builder.append(
                        twoDArrayOfStringRegex.findAll(currentInput).map { it.value }.joinToString("\n") {
                            if(it.trim().equals("[]")) "$$$$$$$$$$" else it.trim().removePrefix("[").removeSuffix("]").split(",").joinToString(" ") { it.trim() }
                        }
                    )
                }
            } else if(is1DList(currentMask)) {
                val inputs = currentInput.trim().removePrefix("[").removeSuffix("]").split(",")
                builder.append("${inputs.size}\n")
                builder.append(
                    inputs.joinToString("\n") {
                        if(isSingleType(currentMask)) {
                            it.trim()
                        } else {
                            it.trim().removeSurrounding("\"")
                        }
                    }
                )
            } else {
                if(isStringType(currentMask)) {
                    builder.append(""""$$currentInput"""")
                } else {
                    builder.append(currentInput)
                }
            }

            builder.append("\n")
        }

        return builder.toString()
    }
}