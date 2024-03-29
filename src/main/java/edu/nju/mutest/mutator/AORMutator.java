package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.sun.jdi.Field;
import edu.nju.mutest.visitor.collector.BinaryExprCollector;
import edu.nju.mutest.visitor.collector.cond.NumericLiteralCond;

import java.util.List;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;

public class AORMutator extends AbstractMutator{

    private List<BinaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();
    private BinaryExpr.Operator[] targetOps = {
            PLUS, MINUS, MULTIPLY, DIVIDE, REMAINDER
    };

    public AORMutator(CompilationUnit cu) {
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
            Expression left = mp.getLeft();
            Expression right = mp.getRight();
            if(left instanceof StringLiteralExpr || left instanceof CharLiteralExpr || left instanceof FieldAccessExpr) continue;
            if(right instanceof StringLiteralExpr || right instanceof CharLiteralExpr|| right instanceof FieldAccessExpr) continue;
/*            if(!(left instanceof LiteralExpr || left instanceof UnaryExpr || left instanceof BinaryExpr)) continue;*/
/*            if(!(right instanceof LiteralExpr || right instanceof UnaryExpr || right instanceof BinaryExpr)) continue;*/

            // This is a polluted operation. So we preserve the original
            // operator for recovering.
            BinaryExpr.Operator origOp = mp.getOperator();

            // Generate simple mutation. Each mutant contains only one
            // mutated point.
            boolean flag = false;
            for (BinaryExpr.Operator targetOp: targetOps){
                if(origOp.equals(targetOp)){
                    flag = true;
                    break;
                }
            }

            if(!flag) continue;


            for (BinaryExpr.Operator targetOp : targetOps) {
                // Skip self
                if (origOp.equals(targetOp))
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

}
