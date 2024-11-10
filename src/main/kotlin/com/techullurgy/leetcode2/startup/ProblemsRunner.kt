package com.techullurgy.leetcode2.startup

import com.techullurgy.leetcode2.data.entities.FileContent
import com.techullurgy.leetcode2.data.entities.Problem
import com.techullurgy.leetcode2.data.entities.Snippet
import com.techullurgy.leetcode2.data.repositories.ProblemsRepository
import com.techullurgy.leetcode2.domain.mappers.toTestcase
import com.techullurgy.leetcode2.domain.model.Difficulty
import com.techullurgy.leetcode2.domain.model.ProgrammingLanguage
import com.techullurgy.leetcode2.domain.model.TestcaseInputCollectionType
import com.techullurgy.leetcode2.domain.model.TestcaseInputDetails
import com.techullurgy.leetcode2.domain.model.TestcaseInputType
import com.techullurgy.leetcode2.network.models.TestcaseInputModel
import com.techullurgy.leetcode2.network.models.TestcaseModel
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ProblemsRunner(
    private val problemsRepository: ProblemsRepository,
): CommandLineRunner {
    override fun run(vararg args: String?) {
        problemsRepository.saveAll(problems)
    }
}

private val problems = listOf(
    Problem(
        title = "Add Two numbers",
        description = "<p>Given integer <code>n</code>, Return a number which is multiplied by 2 with <code>n</code></p>",
        difficulty = Difficulty.Easy,
        snippet = Snippet(
            c = """
                int addTwo(int a, int b) {
                
                }
            """.trimIndent(),
            cpp = """
                int addTwo(int a, int b) {
                
                }
            """.trimIndent(),
            java = """
                class Solution {
                    public int add(int n) {
                    
                    }
                }
            """.trimIndent(),
            python = """
                def addTwo(a: int, b: int):
                    
            """.trimIndent(),
            javascript = """
                function addTwo(int a, int b) {
                
                }
            """.trimIndent(),
        ),
        fileContent = FileContent(
            c = """
                #include<stdio.h>
                #include<stdlib.h>
                #include<string.h>
                
                void logToOutput(int tNo) {
                  char filePath[100];
                  sprintf(filePath, "outputs/output%d.txt", tNo);
                  freopen(filePath, "a", stdout);
                }
    
                void logToResult(int tNo) {
                  char filePath[100];
                  sprintf(filePath, "outputs/result%d.txt", tNo);
                  freopen(filePath, "a", stdout);
                }
    
                void logToExpectedResult(int tNo) {
                  char filePath[100];
                  sprintf(filePath, "outputs/eResult%d.txt", tNo);
                  freopen(filePath, "a", stdout);
                }
                
                int getSingleInteger(FILE*);
                long getSingleLong(FILE*);
                char getSingleChar(FILE*);
                char* getString(FILE*);
                
                char** getArrayOfStrings(FILE*);
                int* getArrayOfInt(FILE*, int);
                
                int** getArrayOfArrayOfInt(FILE*);
                char*** getArrayOfArrayOfStrings(FILE*);
                
                int getSingleInteger(FILE* file) {
                  int a;
                  char line[1024];
                  fgets(line, sizeof(line), file);
                  sscanf(line, "%d", &a);
                  return a;
                }
                
                char* getString(FILE* file) {
                  char line[1024];
                  fgets(line, sizeof(line), file);
                  size_t length = strlen(line);
                  if (length > 0 && line[length - 1] == '\n') {
                      line[length - 1] = '\0';  // Replace newline with null terminator
                  }
                  char* a = (char*)malloc(sizeof(line));
                  strcpy(a, line);
                  return a;
                }
                
                char** getArrayOfStrings(FILE* file) {
                  int arraySize = getSingleInteger(file);
                
                  char** a = (char**)malloc(arraySize * sizeof(char*));
                
                  for(int i=0; i<arraySize; i++) {
                    char* line = getString(file);
                    a[i] = (char*)malloc(sizeof(line));
                    strcpy(a[i],line);
                  }
                
                  return a;
                }
                
                char*** getArrayOfArrayOfStrings(FILE* file) {
                  int rowSize = getSingleInteger(file);
                
                  char*** a = (char***)malloc(rowSize * sizeof(char**));
                
                  for(int i=0; i<rowSize; i++) {
                    int colSize = getSingleInteger(file);
                    a[i] = (char**)malloc(colSize*sizeof(char*));
                
                    for(int j = 0; j < colSize; j++) {
                      char* line = getString(file);
                      a[i][j] = (char*)malloc(sizeof(line));
                      strcpy(a[i][j],line);
                    }
                  }
                
                  return a;
                }
                
                int originalAddTwo(int a, int b) {
                    return a+b;
                }
                
                int addTwo(int, int);
                
                int main(int argc, char *argv[]) {
                  int tNo = atoi(argv[1]);
                  char filePath[100]; 
                  sprintf(filePath, "/tmp/${ProgrammingLanguage.C.usage}/testcases/input%d.txt", tNo);
                  FILE* file = fopen(filePath, "r");
                  
                  // Your code here
                  int a = getSingleInteger(file);
                  int b = getSingleInteger(file);
                  
                  int original = originalAddTwo(a, b);
                  
                  logToOutput(tNo);
                  int result = addTwo(a, b);
                  
                  logToResult(tNo);
                  printf("%d", result);
                  logToExpectedResult(tNo);
                  printf("%d", original);
                  
                  if(result != original) return 168;
                }
                
                *****CODE*****
            """.trimIndent(),
            creplaceStr = "*****CODE*****",
            cpp = "",
            cppReplaceStr = "",
            java = """
                import java.io.*;
                import java.util.*;
    
                class MainUtils {
                  private static Map<Integer, String> map = new HashMap();
                  private static int lineIndex = 1;
    
                  public static void readFromFileAndSaveInMap(String fileName) {
                    try(Scanner scanner = new Scanner(new File(fileName))) {
                      while(scanner.hasNextLine()) {
                        String current = scanner.nextLine();
                        map.put(lineIndex++, current);
                      }
                      lineIndex = 1;
                    } catch(FileNotFoundException e) {}
                  }
    
                  public static int getInteger() {
                    return Integer.parseInt(map.get(lineIndex++));
                  }
    
                  public static String getString() {
                    return map.get(lineIndex++);
                  }
    
                  public static List<String> getListOfStrings() {
                    List<String> ans = new ArrayList();
                    int length = getInteger();
                    for(int i = 0; i < length; i++) {
                      ans.add(getString());
                    }
                    return ans;
                  }
    
                  public static List<List<String>> getListOfListOfStrings() {
                    List<List<String>> ans = new ArrayList();
                    int rows = getInteger();
                    for(int i = 0; i < rows; i++) {
                      List<String> inner = new ArrayList();
                      int columns = getInteger();
                      for(int j = 0; j < columns; j++) {
                        inner.add(getString());
                      }
                      ans.add(new ArrayList(inner));
                    }
                    return ans;
                  }
                }
                
                class OriginalSolution {
                    public int addTwo(int a, int b) {
                        return a+b;
                    }
                }
                
                *****CODE*****
    
                public class Main {
                  public static void main(String[] args) throws Exception {
                    MainUtils.readFromFileAndSaveInMap("/tmp/${ProgrammingLanguage.Java.usage}/testcases/input"+args[0]+".txt");
                    
                    // Your Code Here
                    int a = MainUtils.getInteger();
                    int b = MainUtils.getInteger();
                    
                    OriginalSolution osol = new OriginalSolution();
                    int eResult = osol.addTwo(a, b);
                    
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
                    int result = sol.addTwo(a, b);
                    
                    System.setOut(userFileResult);
                    System.out.print(result);
                    System.setOut(userFileEResult);
                    System.out.print(eResult);


                    if(result != eResult) {
                        System.exit(168);
                    }
                    // End of your code
                  }
                }
            """.trimIndent(),
            javaReplaceStr = "*****CODE*****",
            python = """
                from pathlib import Path
                import sys
                
                ___input_map___ = dict()
                ___line_index___ = 1
                
                ___path___ = Path('outputs')
                ___path___.mkdir(parents=True, exist_ok=True)
                
                def logToOutput():
                  _filename = "outputs/output{}.txt".format(sys.argv[1])
                  sys.stdout = open(_filename, "a")
                
                def logToExpectedResult():
                  _filename = "outputs/eResult{}.txt".format(sys.argv[1])
                  sys.stdout = open(_filename, "a")
                
                def logToResult():
                  _filename = "outputs/result{}.txt".format(sys.argv[1])
                  sys.stdout = open(_filename, "a")
    
                def readFromFileAndSaveInDictionary(filename):
                  global ___line_index___
                  with open(filename, "r") as file:
                    line = file.readline()
                    while line:
                      ___input_map___[___line_index___] = line.strip()
                      ___line_index___ = ___line_index___ + 1
                      line = file.readline()
                  ___line_index___ = 1
                  
    
                def getString():
                  global ___line_index___
                  ans = ___input_map___[___line_index___]
                  ___line_index___ = ___line_index___+1
                  return ans
    
                def getInteger():
                  global ___line_index___
                  ans = int(___input_map___[___line_index___])
                  ___line_index___ = ___line_index___+1
                  return ans
    
                def getArrayOfStrings():
                  ans = list()
                  length = getInteger()
                  for i in range(length):
                    ans.append(getString())
                  return ans
    
                def getArrayOfIntegers():
                  ans = list()
                  length = getInteger()
                  for i in range(length):
                    ans.append(getInteger())
                  return ans
    
                def getArrayOfArrayOfStrings():
                  ans = list()
                  rows = getInteger()
                  for i in range(rows):
                    inner = list()
                    columns = getInteger()
                    for j in range(columns):
                      inner.append(getString())
                    ans.append(inner)
                  return ans
    
                def getArrayOfArrayOfIntegers():
                  ans = list()
                  rows = getInteger()
                  for i in range(rows):
                    inner = list()
                    columns = getInteger()
                    for j in range(columns):
                      inner.append(getInteger())
                    ans.append(inner)
                  return ans
    
                readFromFileAndSaveInDictionary("/tmp/${ProgrammingLanguage.Python.usage}/testcases/input{}.txt".format(sys.argv[1]))
                
                # Your Code Here
                # sys.exit(168) for wrong answer
                
                def originalAddTwo(a: int, b: int):
                  return a+b
                
                *****CODE*****
                
                a = getInteger()
                b = getInteger()
                
                eResult = originalAddTwo(a,b)
                
                logToOutput()
                result = addTwo(a,b)
                
                logToResult()
                print(result)
                logToExpectedResult()
                print(eResult)
                
                if(result != eResult):
                  sys.exit(168)
            """.trimIndent(),
            pythonReplaceStr = "*****CODE*****",
            javascript = """
                const fs = require('fs');
                const readline = require('readline')

                let ___input_map___ = new Map();
                let ___line_index___ = 1
                
                const tNo = process.argv[2]

                async function readFromFileAndSaveInMap(fileName) {
                  const fileStream = fs.createReadStream(fileName)
                  const rl = readline.createInterface({
                    input: fileStream,
                    crlfDelay: Infinity
                  })

                  return new Promise(res => {
                    rl.on('line', line => ___input_map___.set(___line_index___++, line))
                    rl.on('close', _ => {
                        ___line_index___ = 1
                        res()
                    })
                  })
                }


                function getString() {
                  return ___input_map___.get(___line_index___++)
                }

                function getNumber() {
                  return +(___input_map___.get(___line_index___++))
                }

                function getArrayOfStrings() {
                  const ans = []
                  const length = getNumber()
                  for(let i = 0; i < length; i++) {
                    ans.push(getString())
                  }
                  return ans;
                }

                function getArrayOfArrayOfStrings() {
                  const ans = []
                  const rows = getNumber()
                  for(let i = 0; i < rows; i++) {
                    ans.push([])
                    const columns = getNumber()
                    for(let j = 0; j < columns; j++) {
                      ans[i].push(getString())
                    }
                  }
                  return ans;
                }

                function getArrayOfArrayOfNumbers() {
                  const ans = []
                  const rows = getNumber()
                  for(let i = 0; i < rows; i++) {
                    ans.push([])
                    const columns = getNumber()
                    for(let j = 0; j < columns; j++) {
                      ans[i].push(getNumber())
                    }
                  }
                  return ans;
                }

                const ___path___ = 'outputs'

                if (!fs.existsSync(___path___)) {
                  fs.mkdirSync(___path___, { recursive: true });
                }
                
                let logOutputFile = fs.createWriteStream('outputs/output'+tNo+'.txt', { flags: 'a' }); // 'a' for appending
                let logResultFile = fs.createWriteStream('outputs/result'+tNo+'.txt', { flags: 'a' }); // 'a' for appending
                let logExpectedResultFile = fs.createWriteStream('outputs/eResult'+tNo+'.txt', { flags: 'a' }); // 'a' for appending
                
                // Create a writable stream to the log file
                let logFile = logOutputFile
                
                function logToOutput() {
                  logFile = logOutputFile
                }
                
                function logToResult() {
                  logFile = logResultFile
                }
                
                function logToExpectedResult() {
                  logFile= logExpectedResultFile
                }
                
                // Redirect console.log to write to the file
                console.log = (message) => {
                  logFile.write(message+"");
                };
                
                function originalAdd(a, b) {
                    return a+b
                }
                
                *****CODE*****
                
                (async () => {
                  await readFromFileAndSaveInMap('/tmp/${ProgrammingLanguage.Javascript.usage}/testcases/input'+tNo+'.txt')
                  
                  // Your Code Here
                  const a = getNumber()
                  const b = getNumber()
                  
                  const eResult = originalAdd(a,b)
                  
                  logToOutput()
                  const result = addTwo(a, b)
                  
                  logToResult()
                  console.log(result)
                  logToExpectedResult()
                  console.log(eResult)
                  
                  Promise.all([
                    new Promise(res => logOutputFile.end(res)),
                    new Promise(res => logResultFile.end(res)),
                    new Promise(res => logExpectedResultFile.end(res)),
                  ]).then(_ => {
                    if(result !== eResult) {
                      process.exit(168)
                    }  
                  })
                  
                })()

            """.trimIndent(),
            javascriptReplaceStr = "*****CODE*****"
        ),
        testcases = listOf(
            TestcaseModel(
                isHidden = false,
                inputs = listOf(
                    TestcaseInputModel(
                        value = "24",
                        details = TestcaseInputDetails(
                            name = "x",
                            type = TestcaseInputType.NON_STRING,
                            collectionType = TestcaseInputCollectionType.SINGLE,
                            displayOrder = 1
                        )
                    ),
                    TestcaseInputModel(
                        value = "98",
                        details = TestcaseInputDetails(
                            name = "y",
                            type = TestcaseInputType.NON_STRING,
                            collectionType = TestcaseInputCollectionType.SINGLE,
                            displayOrder = 2
                        )
                    ),
                )
            ),
            TestcaseModel(
                isHidden = false,
                inputs = listOf(
                    TestcaseInputModel(
                        value = "-24",
                        details = TestcaseInputDetails(
                            name = "x",
                            type = TestcaseInputType.NON_STRING,
                            collectionType = TestcaseInputCollectionType.SINGLE,
                            displayOrder = 1
                        )
                    ),
                    TestcaseInputModel(
                        value = "-8",
                        details = TestcaseInputDetails(
                            name = "y",
                            type = TestcaseInputType.NON_STRING,
                            collectionType = TestcaseInputCollectionType.SINGLE,
                            displayOrder = 2
                        )
                    ),
                )
            ),
            TestcaseModel(
                isHidden = false,
                inputs = listOf(
                    TestcaseInputModel(
                        value = "67",
                        details = TestcaseInputDetails(
                            name = "x",
                            type = TestcaseInputType.NON_STRING,
                            collectionType = TestcaseInputCollectionType.SINGLE,
                            displayOrder = 1
                        )
                    ),
                    TestcaseInputModel(
                        value = "528",
                        details = TestcaseInputDetails(
                            name = "y",
                            type = TestcaseInputType.NON_STRING,
                            collectionType = TestcaseInputCollectionType.SINGLE,
                            displayOrder = 2
                        )
                    ),
                )
            )
        ).map { it.toTestcase() }.toMutableList()
    )
)