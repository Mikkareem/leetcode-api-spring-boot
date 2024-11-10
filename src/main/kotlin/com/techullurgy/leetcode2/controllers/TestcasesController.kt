package com.techullurgy.leetcode2.controllers

import com.techullurgy.leetcode2.data.entities.Testcase
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.domain.mappers.toTestcase
import com.techullurgy.leetcode2.domain.mappers.toTestcaseInputDetails
import com.techullurgy.leetcode2.domain.mappers.toTestcaseModel
import com.techullurgy.leetcode2.domain.model.TestcaseInputDetails
import com.techullurgy.leetcode2.network.models.TestcaseModel
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/problems/crud/{pid}/testcases")
class TestcasesController(
    private val problemsRepository: ProblemsRepository,
) {

    @GetMapping("/formats")
    fun getTestcaseFormatsForProblem(
        @PathVariable("pid") problemId: String
    ): ResponseEntity<List<TestcaseInputDetails>> {
        val problem = problemsRepository.findById(Integer.parseInt(problemId)).getOrNull() ?: return ResponseEntity.notFound().build()
        val testcaseDetails = problem.testcaseFormats.map { it.toTestcaseInputDetails() }
        return ResponseEntity.ok(testcaseDetails)
    }

    @GetMapping
    fun getAllTestcasesForProblem(
        @PathVariable("pid") problemId: String
    ): ResponseEntity<List<TestcaseModel>> {
        val problem = problemsRepository.findById(Integer.parseInt(problemId)).getOrNull() ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(problem.testcases.map { it.toTestcaseModel() })
    }

    @GetMapping("/{tid}")
    fun getTestcaseForProblem(
        @PathVariable("pid") problemId: String,
        @PathVariable("tid") testcaseId: String
    ): ResponseEntity<TestcaseModel> {
        val problem = problemsRepository.findById(Integer.parseInt(problemId)).getOrNull() ?: return ResponseEntity.notFound().build()
        val testcase = problem.testcases.firstOrNull { it.testcaseId == testcaseId.toLong() } ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(testcase.toTestcaseModel())
    }

    @Transactional
    @PostMapping
    fun createTestcaseForTheProblem(
        @PathVariable("pid") problemId: String,
        @RequestBody model: TestcaseModel
    ): ResponseEntity<Unit> {
        val problem = problemsRepository.findById(Integer.parseInt(problemId)).getOrNull() ?: return ResponseEntity.notFound().build()
        val testcase = model.toTestcase()

        problem.testcases.add(testcase)
        problemsRepository.save(problem)

        return ResponseEntity.ok().build()
    }

    @Transactional
    @PutMapping
    fun updateTestcaseForTheProblem(
        @PathVariable("pid") problemId: String,
        @RequestBody model: TestcaseModel
    ): ResponseEntity<Unit> {
        val problem = problemsRepository.findById(Integer.parseInt(problemId)).getOrNull() ?: return ResponseEntity.notFound().build()
        val testcase = model.toTestcase()

        println(problem.testcases)

        problem.testcases.removeIf {
            it.testcaseId == model.id
        }
        problem.testcases.add(testcase)
        problemsRepository.save(problem)

        return ResponseEntity.ok().build()
    }

    @Transactional
    @DeleteMapping("/{tid}")
    fun deleteTestcaseForProblem(
        @PathVariable("pid") problemId: String,
        @PathVariable("tid") testcaseId: Long,
    ): ResponseEntity<Unit> {
        val problem = problemsRepository.findById(Integer.parseInt(problemId)).getOrNull() ?: return ResponseEntity.notFound().build()

        if(!problem.testcases.any { it.testcaseId == testcaseId }) return ResponseEntity.notFound().build()

        problem.testcases.removeIf { it.testcaseId == testcaseId }
        problemsRepository.save(problem)
        return ResponseEntity.ok().build()
    }
}