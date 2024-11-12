package com.techullurgy.leetcode2.controllers

import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.data.repositories.SubmissionsRepository
import com.techullurgy.leetcode2.data.repositories.UsersRepository
import com.techullurgy.leetcode2.domain.mappers.toProblemDTO
import com.techullurgy.leetcode2.domain.mappers.toProblemListItem
import com.techullurgy.leetcode2.domain.mappers.toProblemTestcase
import com.techullurgy.leetcode2.domain.mappers.toSubmissionDTO
import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.model.TestcaseResult
import com.techullurgy.leetcode2.domain.services.CodeExecutionService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull
import kotlin.random.Random

@RestController
class AlgorithmsController(
    private val problemRepository: ProblemsRepository,
    private val submissionsRepository: SubmissionsRepository,
    private val userRepository: UsersRepository,
    private val codeExecutionService: CodeExecutionService
) {

    data class SnippetResponse(
        val snippet: String
    )

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
        val submissions = submissionsRepository.findByProblemIdAndUserId(problem.problemNo, user.id).map { it.toSubmissionDTO() }

        return ResponseEntity.ok(ProblemByIdResponse(problem, submissions, user))
    }

    @GetMapping("/problem/{problemNo}/snippets")
    fun getSnippet(
        @PathVariable("problemNo") problemNo: Int,
        @RequestParam("language", required = true) language: ProgrammingLanguage
    ): ResponseEntity<SnippetResponse> {
        val snippet = problemRepository.findById(problemNo).get().snippet

        val resultantSnippet = when(language) {
            ProgrammingLanguage.C -> snippet.c
            ProgrammingLanguage.Cpp -> snippet.cpp
            ProgrammingLanguage.Java -> snippet.java
            ProgrammingLanguage.Python -> snippet.python
            ProgrammingLanguage.Javascript -> snippet.javascript
        }

        return ResponseEntity.ok(SnippetResponse(resultantSnippet))
    }

    @PostMapping("/problem/{problemNo}/run")
    fun runCodeForTheProblemForTheUser(@PathVariable("problemNo") problemNo: Int, @RequestBody codeRequest: CodeRequest): ResponseEntity<RunResultResponse> {
        val userId = "laksd9832kjshkd"

        val executableTestcases = codeRequest.sampleTestcases

//        val results = codeExecutionService.executeFor(
//            userId = userId,
//            problemId = problemNo,
//            userCode = codeRequest.userCode,
//            language = codeRequest.language,
//            testcases = executableTestcases.map { it.toProblemTestcase() }
//        )

        Thread.sleep(5000)

        val result2 = codeRequest.sampleTestcases.mapIndexed { index, it ->
            TestcaseResult(
                testcase = it.toProblemTestcase(),
                expectedResult = "${index + Random.nextInt()}",
                yourResult = "${index + Random.nextInt()}",
                stdout = "",
                result = CodeSubmissionResult.Accepted
            )
        }

        return ResponseEntity.ok(
            RunResultResponse(
                problemId = problemNo,
                results = result2.map { r ->
                    TestcaseResultDTO(
                        testcase = executableTestcases.first { it.id == r.testcase.id },
                        expectedResult = r.expectedResult,
                        yourResult = r.yourResult,
                        stdout = r.stdout,
                        result = r.result
                    )
                }
            )
        )
    }

    fun submitCodeForTheProblemForTheUser(
        @PathVariable("problemNo") problemNo: Int,
        @RequestBody codeRequest: CodeRequest
    ) {
        val userId = "laksd9832kjshkd"

        val problem = problemRepository.findById(problemNo).getOrNull() ?: return

        val executableTestcases = (codeRequest.sampleTestcases.map { it.toProblemTestcase() } +
                problem.testcases.filter { it.isHidden == true }.map { it.toProblemTestcase() }).toSet().toList()

        val results = codeExecutionService.executeFor(
            userId = userId,
            problemId = problemNo,
            userCode = codeRequest.userCode,
            language = codeRequest.language,
            testcases = executableTestcases
        )

        val executedTestcasesCount = results.count { it.result != CodeSubmissionResult.NotExecuted }
        val totalTestcasesCount = results.count()

        val executedResults = results.filter { it.result != CodeSubmissionResult.NotExecuted }

        val verdict = if(executedResults.any { it.result != CodeSubmissionResult.Accepted }) {
            executedResults.first { it.result != CodeSubmissionResult.Accepted  }.result
        } else CodeSubmissionResult.Accepted

        if(verdict == CodeSubmissionResult.Accepted) {

        } else {

        }
    }
}