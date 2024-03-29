package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import edu.nju.mutest.visitor.collector.BinaryExprCollector;

import java.util.List;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;

public class LCRMutator extends AbstractMutator {
    private List<BinaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();
    private BinaryExpr.Operator[] targetOps1 = {
            BinaryExpr.Operator.BINARY_AND,
            BinaryExpr.Operator.BINARY_OR,
            BinaryExpr.Operator.XOR,
    };
    private BinaryExpr.Operator[] targetOps2 = {
            AND,
            OR
    };

    public LCRMutator(CompilationUnit cu) {
        super(cu);
    }


    @Override
    public void locateMutationPoints() {
        mutPoints = BinaryExprCollector.collect(this.origCU);
    }

    public List<CompilationUnit> mutate(){
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
            for (BinaryExpr.Operator targetOp : targetOps1) {
                // Skip self
                if (origOp.equals(targetOp) || !containsTargetOperator1(origOp))
                    continue;
                // Mutate
                mutants.add(mutateOnce(mp, targetOp));
            }
            for (BinaryExpr.Operator targetOp : targetOps2) {
                // Skip self
                if (origOp.equals(targetOp) || !containsTargetOperator2(origOp))
                    continue;
                // Mutate
                mutants.add(mutateOnce(mp, targetOp));
            }

            // Recovering
            mp.setOperator(origOp);

        }

        return this.mutants;
    }

    private CompilationUnit mutateOnce(BinaryExpr mp, BinaryExpr.Operator op) {
        mp.setOperator(op);
        // Now the CU is a mutated one. Return its clone.
        return this.origCU.clone();
    }

    private boolean containsTargetOperator1(BinaryExpr.Operator op) {
        for (BinaryExpr.Operator targetOp : targetOps1) {
            if (op.equals(targetOp)) {
                return true;
            }
        }
        return false;
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
