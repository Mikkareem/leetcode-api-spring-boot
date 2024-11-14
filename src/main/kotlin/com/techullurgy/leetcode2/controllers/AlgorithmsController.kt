package com.techullurgy.leetcode2.controllers

import com.techullurgy.leetcode2.data.entities.AcceptedProps
import com.techullurgy.leetcode2.data.entities.FailedTestcase
import com.techullurgy.leetcode2.data.entities.FailedTestcaseInput
import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import com.techullurgy.leetcode2.data.entities.Submission
import com.techullurgy.leetcode2.data.entities.WrongAnswerProps
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.data.repositories.SubmissionsRepository
import com.techullurgy.leetcode2.data.repositories.UsersRepository
import com.techullurgy.leetcode2.domain.mappers.toProblemDTO
import com.techullurgy.leetcode2.domain.mappers.toProblemListItem
import com.techullurgy.leetcode2.domain.mappers.toProblemTestcase
import com.techullurgy.leetcode2.domain.mappers.toSubmissionDTO
import com.techullurgy.leetcode2.domain.mappers.toTestcase
import com.techullurgy.leetcode2.domain.model.CodeExecutionType
import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.services.CodeExecutionService
import com.techullurgy.leetcode2.network.models.TestcaseResultDTO
import com.techullurgy.leetcode2.network.requests.CodeRequest
import com.techullurgy.leetcode2.network.responses.ProblemByIdResponse
import com.techullurgy.leetcode2.network.responses.ProblemsListResponse
import com.techullurgy.leetcode2.network.responses.RunResultResponse
import com.techullurgy.leetcode2.network.responses.SubmissionResultResponse
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull
import kotlin.random.Random
import kotlin.random.nextLong

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
                problems = problemRepository.findAll(Sort.by("id")).map { it.toProblemListItem() }
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
        val userId = userRepository.findAll().random().name.lowercase()

        val executableTestcases = codeRequest.sampleTestcases

        val results = codeExecutionService.executeFor(
            userId = userId,
            problemId = problemNo,
            userCode = codeRequest.userCode,
            language = codeRequest.language,
            testcases = executableTestcases.map { it.toProblemTestcase() },
            executionType = CodeExecutionType.RUN
        )

        return ResponseEntity.ok(
            RunResultResponse(
                problemId = problemNo,
                results = results.map { r ->
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

    @Transactional
    @PostMapping("/problem/{problemNo}/submit")
    fun submitCodeForTheProblemForTheUser(
        @PathVariable("problemNo") problemNo: Int,
        @RequestBody codeRequest: CodeRequest
    ): ResponseEntity<Map<String, String>> {
        val user = userRepository.findAll().first()

        val problem = problemRepository.findById(problemNo).getOrNull() ?: return ResponseEntity.notFound().build()

        val executableTestcases = (codeRequest.sampleTestcases.map { it.toTestcase() } +
                problem.testcases.filter { it.isHidden }).toSet().toList()

        val results = codeExecutionService.executeFor(
            userId = user.name.lowercase(),
            problemId = problemNo,
            userCode = codeRequest.userCode,
            language = codeRequest.language,
            testcases = executableTestcases.map { it.toProblemTestcase() },
            executionType = CodeExecutionType.SUBMIT
        )

        val executedResults = results.filter { it.result != CodeSubmissionResult.NotExecuted }

        val verdict = if(executedResults.any { it.result != CodeSubmissionResult.Accepted }) {
            executedResults.first { it.result != CodeSubmissionResult.Accepted  }.result
        } else CodeSubmissionResult.Accepted

        val savedSubmission = Submission(
            language = codeRequest.language,
            code = codeRequest.userCode,
            time = LocalDateTime.now(),
            verdict = verdict,
            problem = problem,
            user = user
        ).run {
            if(verdict == CodeSubmissionResult.Accepted) {
                copy(
                    acceptedProps = AcceptedProps(
                        executionTime = Random.nextLong(),
                        memoryConsumption = Random.nextLong()
                    )
                )
            } else {
                val result = executedResults.first { it.result != CodeSubmissionResult.Accepted }

                copy(
                    wrongAnswerProps = WrongAnswerProps(
                        totalTestcases = results.count(),
                        executedTestcases = executedResults.count(),
                        failedTestcase = FailedTestcase(
                            expectedOutput = result.expectedResult,
                            yourOutput = result.yourResult,
                            stdout = result.stdout,
                            inputs = executableTestcases.first { it.testcaseId == result.testcase.id }.inputs.map {
                                FailedTestcaseInput(
                                    name = it.format.name,
                                    input = it.value
                                )
                            }
                        )
                    )
                )
            }
        }.run {
            submissionsRepository.save(this)
        }

        val response = HashMap<String, String>().apply {
            put("submissionId", savedSubmission.id.toString())
            put("problemId", problem.id.toString())
            put("userId", user.id.toString())
        }

        return ResponseEntity.ok(response)
    }

    @GetMapping("/problem/{problemNo}/submissions")
    fun getAllSubmissionsForProblemForUser(
        @PathVariable("problemNo") problemNo: Int,
    ): ResponseEntity<List<SubmissionResultResponse>> {
        val user = userRepository.findAll().first()

        val problem = problemRepository.findById(problemNo).getOrNull() ?: return ResponseEntity.notFound().build()

        val response = submissionsRepository.findByProblemIdAndUserId(problem.id, user.id).map {
            if(it.verdict == CodeSubmissionResult.Accepted) {
                SubmissionResultResponse.AcceptedSubmissionResponse(
                    submissionId = it.id.toLong(),
                    problemId = it.problem.id.toLong(),
                    userId = it.user.id.toLong(),
                    submittedCode = it.code,
                    codeLanguage = it.language,
                    submissionTime = it.time,
                    verdict = it.verdict,
                    executionTime = it.acceptedProps!!.executionTime,
                    memoryConsumption = it.acceptedProps.memoryConsumption
                )
            } else {
                SubmissionResultResponse.NonAcceptedSubmissionResponse(
                    submissionId = it.id.toLong(),
                    problemId = it.problem.id.toLong(),
                    userId = it.user.id.toLong(),
                    submittedCode = it.code,
                    codeLanguage = it.language,
                    submissionTime = it.time,
                    verdict = it.verdict,
                    totalTestcasesCount = it.wrongAnswerProps!!.totalTestcases,
                    executedTestcasesCount = it.wrongAnswerProps.executedTestcases,
                    failedTestcase = it.wrongAnswerProps.failedTestcase
                )
            }
        }

        return ResponseEntity.ok(response)
    }

    @GetMapping("/problem/{problemNo}/submissions/{submissionId}")
    fun getSubmissionForProblemForUser(
        @PathVariable("problemNo") problemNo: Int,
        @PathVariable("submissionId") submissionId: Int,
    ): ResponseEntity<SubmissionResultResponse> {
        val user = userRepository.findAll().first()

        val problem = problemRepository.findById(problemNo).getOrNull() ?: return ResponseEntity.notFound().build()

        val response = submissionsRepository.findByProblemIdAndUserId(problem.id, user.id)
            .filter { it.id == submissionId }
            .map {
                if(it.verdict == CodeSubmissionResult.Accepted) {
                    SubmissionResultResponse.AcceptedSubmissionResponse(
                        submissionId = it.id.toLong(),
                        problemId = it.problem.id.toLong(),
                        userId = it.user.id.toLong(),
                        submittedCode = it.code,
                        codeLanguage = it.language,
                        submissionTime = it.time,
                        verdict = it.verdict,
                        executionTime = it.acceptedProps!!.executionTime,
                        memoryConsumption = it.acceptedProps.memoryConsumption
                    )
                } else {
                    SubmissionResultResponse.NonAcceptedSubmissionResponse(
                        submissionId = it.id.toLong(),
                        problemId = it.problem.id.toLong(),
                        userId = it.user.id.toLong(),
                        submittedCode = it.code,
                        codeLanguage = it.language,
                        submissionTime = it.time,
                        verdict = it.verdict,
                        totalTestcasesCount = it.wrongAnswerProps!!.totalTestcases,
                        executedTestcasesCount = it.wrongAnswerProps.executedTestcases,
                        failedTestcase = it.wrongAnswerProps.failedTestcase
                    )
                }
            }
            .run {
                if(isEmpty()) {
                    return ResponseEntity.notFound().build()
                }
                first()
            }

        return ResponseEntity.ok(response)
    }
}