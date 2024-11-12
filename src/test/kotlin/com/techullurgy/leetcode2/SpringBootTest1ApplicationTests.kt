package com.techullurgy.leetcode2

import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.data.repositories.UsersRepository
import com.techullurgy.leetcode2.domain.mappers.toProblemTestcase
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.services.CodeExecutionService
import com.techullurgy.leetcode2.domain.services.UserFileCreator
import com.techullurgy.leetcode2.network.requests.CodeRequest
import com.techullurgy.leetcode2.network.responses.ProblemByIdResponse
import com.techullurgy.leetcode2.network.responses.ProblemsListResponse
import com.techullurgy.leetcode2.network.responses.RunResultResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootTest1ApplicationTests {

	@LocalServerPort
	private var port = 0

	@Autowired
	private lateinit var problemsRepository: ProblemsRepository

	@Autowired
	private lateinit var usersRepository: UsersRepository

	private lateinit var restTemplate: RestTemplate

	@Autowired
	private lateinit var service: CodeExecutionService

	private val baseUrl get() = "http://localhost:$port"

	@BeforeEach
	fun setUp() {
		restTemplate = RestTemplate()
		usersRepository.save(LeetcodeUser(name = "Irsath"))
	}

	@AfterEach
	fun cleanUp() {
		usersRepository.deleteById(1)
	}

	@Test
	fun contextLoads() {
		service.print()
	}

	@Test
	fun getProblemById() {
		val response = restTemplate.getForEntity("$baseUrl/problem/1", ProblemByIdResponse::class.java)
		println(response.body)
	}

	@Test
	fun getProblems() {
		val response = restTemplate.getForEntity("$baseUrl/problems", ProblemsListResponse::class.java)
		println(response.body)
	}

	@Test
	fun isContextFoldersAreDeletedSuccessfullyOnceAfterDone() {
		val userId = "9as98das9a"
		val creator = UserFileCreator(userId, "python").use {
			assertContains(it.file.canonicalPath, "temp/python/$userId")
			assertEquals(true, it.file.exists())
			it
		}
		assertEquals(false, creator.file.exists())
	}

	@Test
	fun testCodeExecutionJava() {
		val userId = "89kas89832"
		val problem = problemsRepository.findById(1).get()

		val userCode = """
			class Solution {
				public int add(int n) {
					return n+2;
				}
			}
		""".trimIndent()

		val results = service.executeFor(
			userId = userId,
			userCode = userCode,
			language = ProgrammingLanguage.Java,
			problemId = problem.id,
			testcases = problem.testcases.map { it.toProblemTestcase() }
		)

		println(results)
	}

	@Test
	fun runCodeJava() {
		val response = restTemplate.getForEntity("$baseUrl/problem/1", ProblemByIdResponse::class.java).body!!

		val codeRequest = CodeRequest(
			problemId = response.problem.problemNo,
			language = ProgrammingLanguage.Java,
			userCode = """
				class Solution {
					public int addTwo(int a, int b) {
						return a+b;
					}
				}
			""".trimIndent(),
			sampleTestcases = response.problem.sampleTestcases
		)

		val runResponse = restTemplate.postForEntity("$baseUrl/problem/1/run", codeRequest, RunResultResponse::class.java).body!!

		println(runResponse)
	}

	@Test
	fun runCodeC() {
		val response = restTemplate.getForEntity("$baseUrl/problem/1", ProblemByIdResponse::class.java).body!!

		val codeRequest = CodeRequest(
			problemId = response.problem.problemNo,
			language = ProgrammingLanguage.C,
			userCode = """
				int addTwo(int a, int b) {
					return a*b;
				}
			""".trimIndent(),
			sampleTestcases = response.problem.sampleTestcases
		)

		val runResponse = restTemplate.postForEntity("$baseUrl/problem/1/run", codeRequest, RunResultResponse::class.java).body!!

		println(runResponse)
	}

	@Test
	fun runCodeJavascript() {
		val response = restTemplate.getForEntity("$baseUrl/problem/1", ProblemByIdResponse::class.java).body!!

		val codeRequest = CodeRequest(
			problemId = response.problem.problemNo,
			language = ProgrammingLanguage.Javascript,
			userCode = """
				function addTwo(a, b) {
					return a*b;
				}
			""".trimIndent(),
			sampleTestcases = response.problem.sampleTestcases
		)

		val runResponse = restTemplate.postForEntity("$baseUrl/problem/1/run", codeRequest, RunResultResponse::class.java).body!!

		println(runResponse)
	}

	@Test
	fun runCodePython() {
		val response = restTemplate.getForEntity("$baseUrl/problem/1", ProblemByIdResponse::class.java).body!!

		val codeRequest = CodeRequest(
			problemId = response.problem.problemNo,
			language = ProgrammingLanguage.Python,
			userCode = """
				def addTwo(a: int, b: int):
				  return a+b
			""".trimIndent(),
			sampleTestcases = response.problem.sampleTestcases
		)

		val runResponse = restTemplate.postForEntity("$baseUrl/problem/1/run", codeRequest, RunResultResponse::class.java).body!!

		println(runResponse)
	}
}
