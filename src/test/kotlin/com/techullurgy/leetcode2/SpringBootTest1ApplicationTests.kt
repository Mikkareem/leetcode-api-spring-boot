package com.techullurgy.leetcode2

import com.techullurgy.leetcode2.data.entities.FileContent
import com.techullurgy.leetcode2.data.entities.LeetcodeUser
import com.techullurgy.leetcode2.data.entities.Problem
import com.techullurgy.leetcode2.data.entities.Snippet
import com.techullurgy.leetcode2.data.entities.Testcase
import com.techullurgy.leetcode2.data.entities.TestcaseInput
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.data.repositories.UsersRepository
import com.techullurgy.leetcode2.domain.mappers.toProblemTestcase
import com.techullurgy.leetcode2.domain.model.Difficulty
import com.techullurgy.leetcode2.domain.model.ProblemTestcase
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.parsers.testcases.TestcaseType
import com.techullurgy.leetcode2.domain.services.CodeExecutionService
import com.techullurgy.leetcode2.domain.services.UserFileCreator
import com.techullurgy.leetcode2.network.models.ProblemDTO
import com.techullurgy.leetcode2.network.models.ProblemListItem
import com.techullurgy.leetcode2.network.responses.ProblemByIdResponse
import com.techullurgy.leetcode2.network.responses.ProblemsListResponse
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate
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
		val problem = Problem(
			title = "",
			description = "",
			difficulty = Difficulty.EASY,
			snippet = Snippet(
				c = "",
				cpp = "",
				java = "",
				python = "",
				javascript = "",
			),
			fileContent = FileContent(
                c = "",
                cReplaceStr = "",
                cpp = "",
                cppReplaceStr = "",
                java = """
					import java.io.*;

					public class Main {
					    public static void main(String[] args) throws Exception {
					        File file = new File("/tmp/java/testcases/input"+args[0]+".txt");
					        BufferedReader reader = new BufferedReader(new FileReader(file));

					        String line = reader.readLine();
					        Integer n = null;

					        if(line != null) {
					            n = Integer.parseInt(line.trim());
					        }

					        if(n != null) {
					            OriginalSolution osol = new OriginalSolution();
					            int eResult = osol.add(n);
					            
					            String outputFileName = "outputs/output" + args[0] + ".txt";
					            File outputFile = new File(outputFileName);
					            outputFile.createNewFile();
					            PrintStream userFileOut = new PrintStream(outputFileName);

					            String resultFileName = "outputs/result" + args[0] + ".txt";
					            File resultFile = new File(resultFileName);
					            resultFile.createNewFile();
					            PrintStream userFileResult = new PrintStream(resultFileName);

					            String eResultFileName = "outputs/eResult" + args[0] + ".txt";
					            File eResultFile = new File(eResultFileName);
					            eResultFile.createNewFile();
					            PrintStream userFileEResult = new PrintStream(eResultFileName);

					            System.setOut(userFileOut);
					            Solution sol = new Solution();
					            int result = sol.add(n);
					            
					            System.setOut(userFileResult);
					            System.out.println(result);
					            System.setOut(userFileEResult);
					            System.out.println(eResult);


					            if(result != eResult) {
					                System.exit(168);
					            }
					        } else {
					            throw new Exception("Parse Error");
					        }
					    }
					}

					*****CODE*****

					class OriginalSolution {
					    public int add(int n) {
					        return n*2;
					    }
					}
				""".trimIndent(),
                javaReplaceStr = "*****CODE*****",
                python = "",
                pythonReplaceStr = "",
                javascript = "",
                javascriptReplaceStr = ""
            )
		)

		val testcases = listOf(
			Testcase(testcaseNo = 1, isHidden = false, problem = problem).apply {
				details.clear()
				details.add(TestcaseInput(inputName = "stocks", inputValue = "2", typeMask = TestcaseType.SINGLE_TYPE, testcase = this))
			},
			Testcase(testcaseNo = 2, isHidden = false, problem = problem).apply {
				details.clear()
				details.add(TestcaseInput(inputName = "stocks", inputValue = "2", typeMask = TestcaseType.SINGLE_TYPE, testcase = this))
			},
			Testcase(testcaseNo = 3, isHidden = false, problem = problem).apply {
				details.clear()
				details.add(TestcaseInput(inputName = "stocks", inputValue = "3", typeMask = TestcaseType.SINGLE_TYPE, testcase = this))
			},
			Testcase(testcaseNo = 4, isHidden = false, problem = problem).apply {
				details.clear()
				details.add(TestcaseInput(inputName = "stocks", inputValue = "4", typeMask = TestcaseType.SINGLE_TYPE, testcase = this))
			},
			Testcase(testcaseNo = 5, isHidden = false, problem = problem).apply {
				details.clear()
				details.add(TestcaseInput(inputName = "stocks", inputValue = "5", typeMask = TestcaseType.SINGLE_TYPE, testcase = this))
			},
			Testcase(testcaseNo = 6, isHidden = false, problem = problem).apply {
				details.clear()
				details.add(TestcaseInput(inputName = "stocks", inputValue = "6", typeMask = TestcaseType.SINGLE_TYPE, testcase = this))
			},
		)

		problem.testcases.apply {
			clear()
			addAll(testcases)
		}

		problemsRepository.save(problem)
		usersRepository.save(LeetcodeUser(name = "Irsath"))
	}

	@AfterEach
	fun cleanUp() {
		problemsRepository.deleteById(1)
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
			problemId = problem.problemNo,
			testcases = problem.testcases.map { it.toProblemTestcase() }
		)

		println(results)
	}
}
