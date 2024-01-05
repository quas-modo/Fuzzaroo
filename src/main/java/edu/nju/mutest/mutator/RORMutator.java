package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import edu.nju.mutest.visitor.collector.BinaryExprCollector;

import java.util.List;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;


public class RORMutator extends AbstractMutator {

    private List<BinaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();

    private BinaryExpr.Operator[] targetOps = {
            PLUS, MINUS, MULTIPLY, DIVIDE
    };

    private BinaryExpr.Operator[] targetOps2 = {
            LESS, LESS_EQUALS, GREATER, GREATER_EQUALS, EQUALS, NOT_EQUALS
    };

    public RORMutator(CompilationUnit cu) {
        super(cu);
    }

    @Override
    public void locateMutationPoints() {
        mutPoints = BinaryExprCollector.collect(this.origCU);
    }

    @Override
    public List<CompilationUnit> mutate() {

        // Sanity check.
        if (this.mutPoints == null)
            throw new RuntimeException("You must locate mutation points first!");

        // Modify each mutation points.
        for (BinaryExpr mp : mutPoints) {
            // This is a polluted operation. So we preserve the original
            // operator for recovering.
            BinaryExpr.Operator origOp = mp.getOperator();

            // Generate simple mutation. Each mutant contains only one
            // mutated point.
            for (BinaryExpr.Operator targetOp : targetOps2) {
                // Skip self
                if (origOp.equals(targetOp) || !containsTargetOperator2(origOp))
                    continue;
                // Mutate
                mutants.add(mutateOnce(mp, targetOp));
            }
            // //一个true一个false
            // mp.setOperator(EQUALS);
            // mutants.add(this.origCU.clone());
            // mp.setOperator(NOT_EQUALS);
            // mutants.add(this.origCU.clone());
            // // Recovering
            // mp.setOperator(origOp);

        }
        return this.mutants;
    }

    /**
     * Replace the operator with a given one
     */
    private CompilationUnit mutateOnce(BinaryExpr mp, BinaryExpr.Operator op) {
        mp.setOperator(op);
        // Now the CU is a mutated one. Return its clone.
        return this.origCU.clone();
    }

    public List<CompilationUnit> getMutants() {
        if (mutants.isEmpty())
            System.out.println("Oops, seems no mutation has been conducted yet. Call mutate() first!");
        return mutants;
    }
    private boolean containsTargetOperator2(BinaryExpr.Operator op) {
        for (BinaryExpr.Operator targetOp : targetOps2) {
            if (op.equals(targetOp)) {
                return true;
            }
        }
        return false;
    }
}
