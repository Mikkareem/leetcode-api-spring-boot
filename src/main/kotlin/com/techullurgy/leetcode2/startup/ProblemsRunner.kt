package com.techullurgy.leetcode2.startup

import com.techullurgy.leetcode2.data.entities.FileContent
import com.techullurgy.leetcode2.data.entities.Problem
import com.techullurgy.leetcode2.data.entities.Snippet
import com.techullurgy.leetcode2.data.entities.Testcase
import com.techullurgy.leetcode2.data.entities.TestcaseInput
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.domain.model.Difficulty
import com.techullurgy.leetcode2.domain.parsers.testcases.TestcaseType
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ProblemsRunner(
    private val problemsRepository: ProblemsRepository,
): CommandLineRunner {
    override fun run(vararg args: String?) {
//        problemsRepository.saveAll(problems)
    }
}

private val problems = listOf(
    Problem(
        title = "Multiply by 2",
        description = "<p>Given integer <code>n</code>, Return a number which is multiplied by 2 with <code>n</code></p>",
        difficulty = Difficulty.Easy,
        snippet = Snippet(
            c = "",
            cpp = "",
            java = """
                class Solution {
                    public int add(int n) {
                    
                    }
                }
            """.trimIndent(),
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
					            System.out.print(result);
					            System.setOut(userFileEResult);
					            System.out.print(eResult);


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
    ).apply {
//        val testcases = listOf(
//            Testcase(testcaseNo = 1, isHidden = false).apply {
//                inputs.clear()
//                inputs.add(TestcaseInput(inputName = "stocks", inputValue = "2", typeMask = TestcaseType.SINGLE_TYPE))
//            },
//            Testcase(testcaseNo = 2, isHidden = false).apply {
//                inputs.clear()
//                inputs.add(TestcaseInput(inputName = "stocks", inputValue = "2", typeMask = TestcaseType.SINGLE_TYPE))
//            },
//            Testcase(testcaseNo = 3, isHidden = false).apply {
//                inputs.clear()
//                inputs.add(TestcaseInput(inputName = "stocks", inputValue = "3", typeMask = TestcaseType.SINGLE_TYPE))
//            },
//            Testcase(testcaseNo = 4, isHidden = true).apply {
//                inputs.clear()
//                inputs.add(TestcaseInput(inputName = "stocks", inputValue = "4", typeMask = TestcaseType.SINGLE_TYPE))
//            },
//            Testcase(testcaseNo = 5, isHidden = true).apply {
//                inputs.clear()
//                inputs.add(TestcaseInput(inputName = "stocks", inputValue = "5", typeMask = TestcaseType.SINGLE_TYPE))
//            },
//            Testcase(testcaseNo = 6, isHidden = true).apply {
//                inputs.clear()
//                inputs.add(TestcaseInput(inputName = "stocks", inputValue = "6", typeMask = TestcaseType.SINGLE_TYPE))
//            },
//        )
//        this.testcases.clear()
//        this.testcases.addAll(testcases)
    }
)