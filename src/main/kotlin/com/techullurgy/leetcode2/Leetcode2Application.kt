package com.techullurgy.leetcode2

import com.techullurgy.leetcode2.data.entities.FileContent
import com.techullurgy.leetcode2.data.entities.Problem
import com.techullurgy.leetcode2.data.entities.Snippet
import com.techullurgy.leetcode2.domain.model.Difficulty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Leetcode2Application

fun main(args: Array<String>) {
	runApplication<Leetcode2Application>(*args)
}

class P {
	private var current = 1

	init {
		Problem(
			title = "Problem title $current",
			description = "Problem description $current",
			difficulty = Difficulty.entries.random(),
			snippet = Snippet(
				c = "Snippet c $current",
				cpp = "Snippet cpp $current",
				java = "Snippet java $current",
				python = "Snippet python $current",
				javascript = "Snippet javascript $current",
			),
			fileContent = FileContent(
				c = "File Content C $current",
				cReplaceStr = "File Content Replace Snippet C $current",
				cpp = "File Content CPP $current",
				cppReplaceStr = "File Content Replace Snippet CPP $current",
				java = "File Content JAVA $current",
				javaReplaceStr = "File Content Replace Snippet JAVA $current",
				python = "File Content PYTHON $current",
				pythonReplaceStr = "File Content Replace Snippet PYTHON $current",
				javascript = "File Content JAVASCRIPT $current",
				javascriptReplaceStr = "File Content Replace Snippet JAVASCRIPT $current"
			)
		)
	}
}

