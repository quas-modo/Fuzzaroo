package edu.nju.mutest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A demo for executing test suite against mutants
 */
public class DemoMutantExecution {

    static String TEST_SUITE_FQN = "edu.nju.mutest.example.RORTestSuite";

    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 2) {
            // Require param for specifying test suite.
            System.out.println("DemoMutantExecution: <testsuite_dir> <mutant_pool_dir>");
            System.exit(0);
        }

//        TEST_SUITE_FQN = args[2];

        File tsDir = new File(args[0]);
        File mutPoolDir = new File(args[1]);
        System.out.println("[LOG] Test suite dir: " + tsDir.getAbsolutePath());
        System.out.println("[LOG] Mutant pool dir: " + mutPoolDir.getAbsolutePath());

        // Locate all mutants
        File[] fns = mutPoolDir.listFiles();
        if (fns == null) {
            System.out.println("[LOG] Find no mutants!" );
            System.exit(0);
        }
        List<File> mutDirs = Arrays.stream(fns)
                .filter(f -> !f.getName().startsWith("."))
                .collect(Collectors.toList());
        int mutNum = mutDirs.size();
        System.out.printf("[LOG] Locate %d mutants\n", mutNum);

        // Execute each mutant
        System.out.println("[LOG] Start to execute mutants...");
        int killedCnt = 0;
        for (File mutDir : mutDirs) {
            System.out.println("[LOG] -------------------------------------------------");
            String mutName = mutDir.getName();
            System.out.println("[LOG] Execute " + mutName);
            boolean killed = execute(tsDir, mutDir);
            if (killed) {
                killedCnt++;
                System.out.println("[LOG] Killed " + mutName);
            } else
                System.out.println("[LOG] Survived " + mutName);
        }

        // Calculate mutation score
        System.out.println("[LOG] ======================================================");
        System.out.printf("[LOG] Stats: %d/%d(#killed/#total), score=%.2f\n",
                killedCnt, mutNum, calScore(killedCnt, mutNum));
    }

    private static String concateClassPath(String...paths) {
        StringBuilder cpBuilder = new StringBuilder();
        // Suitable for Unix system.
        for (int i = 0; i < paths.length; i++) {
            cpBuilder.append(paths[i]);
            if (i != paths.length - 1)
                cpBuilder.append(':');
        }
        return cpBuilder.toString();
    }

    /**
     * Demo execute once.
     *
     * @param tsDir  test suite dir, part of the classpath
     * @param mutDir mutant class dir, part of the classpath
     * @return whether the mutant is killed.
     */
    private static boolean execute(File tsDir, File mutDir) throws IOException, InterruptedException {

        // Build class path.
        String cp = concateClassPath(tsDir.getAbsolutePath(), mutDir.getAbsolutePath());
//        String directory = System.getProperty(tsDir.getAbsolutePath());
//        System.out.println(tsDir.getAbsolutePath());
//        File currentDir = new File(directory);
//        File[] files = currentDir.listFiles();
//        if(files != null){
//            for(File file: files){
//                System.out.println(file.getName());
//            }
//        }

        // Construct executor
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp, TEST_SUITE_FQN);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        // Wait for execution to finish, or we cannot get exit value.
        p.waitFor();

        // Read execution info
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) break;
            System.out.println(line);
        }

        // 0 means survived, not 0 means killed.
        return p.exitValue() != 0;
    }

    private static double calScore(int killedCnt, int totalNum) {
        return ((double) killedCnt / (double) totalNum) * 100;
    }

}
