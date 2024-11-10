package com.techullurgy.leetcode2.controllers

import com.techullurgy.leetcode2.data.entities.FileContent
import com.techullurgy.leetcode2.data.entities.Problem
import com.techullurgy.leetcode2.data.entities.Snippet
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.data.utils.DefaultFileContent
import com.techullurgy.leetcode2.domain.mappers.toTestcaseFormat
import com.techullurgy.leetcode2.domain.mappers.toTestcaseInputDetails
import com.techullurgy.leetcode2.domain.model.Difficulty
import com.techullurgy.leetcode2.network.requests.ProblemCrudRequest
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

typealias ProblemCrudResponse = ProblemCrudRequest

@RestController
@RequestMapping("/problems/crud")
class ProblemsController(
    private val problemsRepository: ProblemsRepository,
) {
    @Transactional
    @GetMapping
    fun getProblemDetailsForCrud(@RequestParam("pid", defaultValue = "-1") problemId: String): ResponseEntity<ProblemCrudResponse> {
        if(problemId == "-1") {
            return ResponseEntity.ok(
                ProblemCrudResponse(
                    title = "",
                    description = "",
                    difficulty = Difficulty.Easy,
                    snippets = Snippet(0,"","","","",""),
                    fileContent = FileContent(
                        id = 0,
                        c = DefaultFileContent.C,
                        creplaceStr = "",
                        cpp = DefaultFileContent.CPP,
                        cppReplaceStr = "",
                        java = DefaultFileContent.JAVA,
                        javaReplaceStr = "",
                        python = DefaultFileContent.PYTHON,
                        pythonReplaceStr = "",
                        javascript = DefaultFileContent.JAVASCRIPT,
                        javascriptReplaceStr = ""
                    ),
                    testcaseFormats = emptyList()
                )
            )
        }

        val problem = problemsRepository.findById(Integer.parseInt(problemId)).getOrNull()
        if(problem == null) {
            return ResponseEntity.notFound().build()
        }

        val testcaseFormats = problem.testcaseFormats.map { it.toTestcaseInputDetails() }

        return ResponseEntity.ok(
            ProblemCrudResponse(
                title = problem.title,
                description = problem.description,
                difficulty = problem.difficulty,
                snippets = problem.snippet,
                fileContent = problem.fileContent,
                testcaseFormats = testcaseFormats
            )
        )
    }

    @Transactional
    @PostMapping
    fun createNewProblem(@RequestBody request: ProblemCrudRequest): ResponseEntity<Unit> {
        val problem = Problem(
            title = request.title,
            description = request.description,
            difficulty = request.difficulty,
            snippet = request.snippets,
            fileContent = request.fileContent
        )
        val savedProblem = problemsRepository.save(problem)

        val testcaseInputDetails = request.testcaseFormats.map { it.toTestcaseFormat() }

        problemsRepository.save(
            savedProblem.copy(
                testcaseFormats = testcaseInputDetails.toMutableList()
            )
        )
        return ResponseEntity.ok().build()
    }

    @Transactional
    @PutMapping
    fun updateProblem(
        @RequestBody request: ProblemCrudRequest
    ): ResponseEntity<Unit> {
        val problem = problemsRepository.findById(request.id).getOrNull() ?: return ResponseEntity.notFound().build()
        val newProblem = problem.copy(
            title = request.title,
            description = request.description,
            difficulty = request.difficulty,
            snippet = request.snippets,
            fileContent = request.fileContent
        )
        val savedProblem = problemsRepository.save(newProblem)

        val testcaseInputDetails = request.testcaseFormats.map { it.toTestcaseFormat() }

        problemsRepository.save(
            savedProblem.copy(
                testcaseFormats = testcaseInputDetails.toMutableList()
            )
        )

        return ResponseEntity.ok().build()
    }

    @Transactional
    @DeleteMapping("/{pid}")
    fun deleteProblem(@PathVariable("pid") problemId: String): ResponseEntity<Unit> {
        val problem = problemsRepository.findById(Integer.parseInt(problemId)).getOrNull() ?: return ResponseEntity.notFound().build()
        problemsRepository.delete(problem)
        return ResponseEntity.ok().build()
    }
}