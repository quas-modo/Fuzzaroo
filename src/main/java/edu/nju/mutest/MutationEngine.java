package edu.nju.mutest;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import edu.nju.mutest.mutator.ABSMutator;
import edu.nju.mutest.mutator.BinaryMutator;
import edu.nju.mutest.mutator.Mutator;
import edu.nju.mutest.mutator.UOIMutator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MutationEngine {
    // choices of
    enum Operator{
        ABS, AOR, LCR, ROR, UOI
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.out.println("DemoSrcMutationEngine: <source_java_file> <mutant_pool_dir> <operator_type>");
            System.out.println("Example params: src/main/java/edu/nju/mutest/example/Calculator.java pool/ abs");
            System.exit(0);
        }

        HashMap<String, Operator> operator = new HashMap<String, Operator>();
        operator.put("abs", Operator.ABS);
        operator.put("aor", Operator.AOR);
        operator.put("lcr", Operator.LCR);
        operator.put("ror", Operator.ROR);
        operator.put("uoi", Operator.UOI);

        // Read in original program(s).
        File srcFile = new File(args[0]);
        File outDir = new File(args[1]);
        String optStr = args[2];
        Operator opt = operator.get(optStr);

        System.out.println("[LOG] Source file: " + srcFile.getAbsolutePath());
        System.out.println("[LOG] Output dir: " + outDir.getAbsolutePath());

        // Initialize mutator(s).
        CompilationUnit cu = StaticJavaParser.parse(srcFile);

        // todo: Choose Operators
        Mutator mutator = null;
        switch (opt){
            case ABS:
                mutator = new ABSMutator(cu);
                break;
            case AOR:
            case LCR:
            case ROR:
            case UOI:
                mutator = new UOIMutator(cu);
                break;
            default:
                System.out.println("This mutator is not available!");
                System.exit(1);
                break;
        }


        // Locate mutation points.
        mutator.locateMutationPoints();

        // Fire off mutation! Mutants can be wrapped.
        List<CompilationUnit> mutCUs = mutator.mutate();
        System.out.printf("[LOG] Generate %d mutants.\n", mutCUs.size());

        // Preserve to local.
        preserveToLocal(outDir, srcFile, cu, mutCUs);

    }

    /**
     * Write mutants to disk.
     */
    private static void preserveToLocal(
            File outDir,
            File srcFile,
            CompilationUnit cu,
            List<CompilationUnit> mutants) throws IOException {

        // Recreate outDir if it is existed.
        if (outDir.exists()) {
            FileUtils.forceDelete(outDir);
            System.out.println("[LOG] Delete existing outDir." );
        }
        boolean mkdirs = outDir.mkdirs();
        if (mkdirs)
            System.out.println("[LOG] Create outDir: " + outDir.getAbsolutePath());


        // Path relevant to package info
        String packPath = "";

        // Get file name.
        String srcFileName = srcFile.getName();

        // Get package info.
        Optional<PackageDeclaration> opPD = cu.getPackageDeclaration();
        if (opPD.isPresent()) {
            // Turn package info like 'edu.nju.ise' to path 'edu/nju/ise/'
            String packInfo = opPD.get().getName().asString();
            packPath = packInfo.replace('.', '/');
        }

        // Write mutant to local.
        String pattern = "mut-%d/%s";
        for (int i = 0 ; i < mutants.size(); i++) {
            // Create directory to preserve the mutant
            File srcFileDir = new File(outDir, String.format(pattern, i + 1, packPath));
            mkdirs = srcFileDir.mkdirs();
            if (mkdirs)
                System.out.println("[LOG] Create src file dir: " + srcFileDir.getAbsolutePath());
            // Write mutant to local.
            File mutSrcFile = new File(srcFileDir, srcFileName);
            FileWriter fw = new FileWriter(mutSrcFile);
            fw.write(mutants.get(i).toString());
            fw.close();
        }

    }

}
