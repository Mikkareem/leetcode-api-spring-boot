package com.techullurgy.leetcode2.startup

import com.techullurgy.leetcode2.data.entities.AcceptedProps
import com.techullurgy.leetcode2.data.entities.FailedTestcase
import com.techullurgy.leetcode2.data.entities.FailedTestcaseInput
import com.techullurgy.leetcode2.data.entities.Submission
import com.techullurgy.leetcode2.data.entities.WrongAnswerProps
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.data.repositories.SubmissionsRepository
import com.techullurgy.leetcode2.data.repositories.UsersRepository
import com.techullurgy.leetcode2.domain.model.CodeSubmissionResult
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import jakarta.transaction.Transactional
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Order(3)
class SubmissionsRunner(
    private val problemsRepository: ProblemsRepository,
    private val usersRepository: UsersRepository,
    private val submissionsRepository: SubmissionsRepository
): CommandLineRunner {
    @Transactional
    override fun run(vararg args: String?) {
        val problem = problemsRepository.findAll().first()
        val user1 = usersRepository.findAll().first()
        val user2 = usersRepository.findAll().last()

        val submission1 = Submission(
            language = ProgrammingLanguage.Java,
            code = "",
            verdict = CodeSubmissionResult.Accepted,
            time = LocalDateTime.now(),
            problem = problem,
            user = user1,
            acceptedProps = AcceptedProps(
                executionTime = 4093L,
                memoryConsumption = 39023L
            )
        )

        val submission2 = Submission(
            language = ProgrammingLanguage.Javascript,
            code = "javascript code",
            verdict = CodeSubmissionResult.WrongAnswer,
            time = LocalDateTime.now(),
            problem = problem,
            user = user2,
            wrongAnswerProps = WrongAnswerProps(
                totalTestcases = 46,
                executedTestcases = 23,
                failedTestcase = FailedTestcase(
                    expectedOutput = "167",
                    stdout = "Hello",
                    yourOutput = "718",
                    inputs = listOf(
                        FailedTestcaseInput(name = "a", input = "2772"),
                        FailedTestcaseInput(name = "b", input = "78"),
                    )
                )
            )
        )

        val submission3 = Submission(
            language = ProgrammingLanguage.Javascript,
            code = "javascript code",
            verdict = CodeSubmissionResult.WrongAnswer,
            time = LocalDateTime.now(),
            problem = problem,
            user = user1,
            wrongAnswerProps = WrongAnswerProps(
                totalTestcases = 46,
                executedTestcases = 23,
                failedTestcase = FailedTestcase(
                    expectedOutput = "167",
                    stdout = "Hello world",
                    yourOutput = "718",
                    inputs = listOf(
                        FailedTestcaseInput(name = "a", input = "2772"),
                        FailedTestcaseInput(name = "b", input = "78"),
                    )
                )
            )
        )

        submissionsRepository.saveAll(listOf(submission1, submission2, submission3))

        println(
            submissionsRepository.findByProblemIdAndUserId(problem.id, user1.id).map {
                if(it.verdict == CodeSubmissionResult.Accepted) {
                    Pair(it.verdict, it.acceptedProps)
                } else {
                    Pair(it.verdict, it.wrongAnswerProps)
                }
            }
        )
    }
}