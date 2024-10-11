package com.techullurgy.leetcode2.controllers

import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.mappers.toProblemListItem
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.data.repositories.SubmissionsRepository
import com.techullurgy.leetcode2.data.repositories.UsersRepository
import com.techullurgy.leetcode2.domain.mappers.toProblemDTO
import com.techullurgy.leetcode2.domain.mappers.toProblemTestcase
import com.techullurgy.leetcode2.domain.mappers.toSubmissionDTO
import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.parsers.testcases.TestcaseType
import com.techullurgy.leetcode2.domain.services.CodeExecutionService
import com.techullurgy.leetcode2.network.models.ProblemListItem
import com.techullurgy.leetcode2.network.models.TestcaseResultDTO
import com.techullurgy.leetcode2.network.requests.CodeRequest
import com.techullurgy.leetcode2.network.responses.ProblemByIdResponse
import com.techullurgy.leetcode2.network.responses.ProblemsListResponse
import com.techullurgy.leetcode2.network.responses.RunResultResponse
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AlgorithmsController(
    private val problemRepository: ProblemsRepository,
    private val submissionsRepository: SubmissionsRepository,
    private val userRepository: UsersRepository,
    private val codeExecutionService: CodeExecutionService
) {

    @GetMapping("/problems")
    fun getAllProblemsForTheUser(): ResponseEntity<ProblemsListResponse> {
        return ResponseEntity.ok(
            ProblemsListResponse(
                problems = problemRepository.findAll(Sort.by("problemNo")).map { it.toProblemListItem() }
            )
        )
    }

    @GetMapping("/problem/{problemNo}")
    fun getProblemByIdForTheUser(@PathVariable("problemNo") problemNo: Int): ResponseEntity<ProblemByIdResponse> {
        val user: LeetcodeUser = userRepository.findById(1).get()
        val problem = problemRepository.findById(problemNo).get().toProblemDTO(ProgrammingLanguage.Java)
        val submissions = submissionsRepository.getAllSubmissionsForProblem(problem.problemNo, user.id).map { it.toSubmissionDTO() }

        return ResponseEntity.ok(ProblemByIdResponse(problem, submissions, user))
    }

    @PostMapping("/problem/{problemNo}/run")
    fun runCodeForTheProblemForTheUser(@PathVariable("problemNo") problemNo: Int, @RequestBody codeRequest: CodeRequest): ResponseEntity<RunResultResponse> {
        val userId = "laksd9832kjshkd"

        val executableTestcases = codeRequest.sampleTestcases

        val results = codeExecutionService.executeFor(
            userId = userId,
            problemId = problemNo,
            userCode = codeRequest.userCode,
            language = codeRequest.language,
            testcases = executableTestcases.map { it.toProblemTestcase() }
        )

        RunResultResponse(
            problemId = problemNo,
            results = results.map { r ->
                TestcaseResultDTO(
                    testcase = executableTestcases.first { it.testcaseNo == r.testcase.id },
                    expectedResult = r.expectedResult,
                    yourResult = r.yourResult,
                    stdout = r.stdout,
                    result = r.result
                )
            }
        )
    }
}