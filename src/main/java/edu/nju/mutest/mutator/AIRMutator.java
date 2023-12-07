package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.nju.mutest.visitor.collector.MethodDeclarationCollector;

import java.util.ArrayList;
import java.util.List;

public class AIRMutator extends AbstractMutator {

    private List<ArrayAccessExpr> mutPoints;
    private List<CompilationUnit> mutants;

    public AIRMutator(CompilationUnit cu) {
        super(cu);
        mutPoints = new ArrayList<>();
        mutants = new ArrayList<>();
    }

    @Override
    public void locateMutationPoints() {
        List<MethodDeclaration> methodDeclarations = MethodDeclarationCollector.collect(this.origCU);

        for (MethodDeclaration method : methodDeclarations) {
            method.accept(new VoidVisitorAdapter<Void>() {
                @Override
                public void visit(ArrayAccessExpr n, Void arg) {
                    super.visit(n, arg);
                    mutPoints.add(n);
                }
            }, null);
        }
    }

    @Override
    public List<CompilationUnit> mutate() {
        if (mutPoints.isEmpty())
            throw new RuntimeException("没有找到适合变异的点！");

        for (ArrayAccessExpr expr : mutPoints) {
            Expression originalIndex = expr.getIndex();
            // 以下为简化的变异实例，您可以根据需要添加更多变异
            Expression[] mutatedIndices = new Expression[]{
                    new BinaryExpr(originalIndex.clone(), new IntegerLiteralExpr(1), BinaryExpr.Operator.PLUS),  // i+1
                    new BinaryExpr(originalIndex.clone(), new IntegerLiteralExpr(1), BinaryExpr.Operator.MINUS), // i-1
                    new IntegerLiteralExpr(0), // 0
            };

            for (Expression mutatedIndex : mutatedIndices) {
                ArrayAccessExpr mutatedExpr = new ArrayAccessExpr(expr.getName().clone(), mutatedIndex);
                mutants.add(insertMutatedExpr(mutatedExpr, expr));
            }
        }
        return mutants;
    }

    private CompilationUnit insertMutatedExpr(Expression mutatedExpr, Expression originalExpr) {
        // 克隆原始 CompilationUnit
        CompilationUnit mutatedCU = this.origCU.clone();

        // 使用访问者模式替换原始表达式为变异后的表达式
        mutatedCU.accept(new ReplaceExpressionVisitor(originalExpr, mutatedExpr), null);

        return mutatedCU;
    }

    private static class ReplaceExpressionVisitor extends VoidVisitorAdapter<Void> {
        private final Expression originalExpr;
        private final Expression newExpr;

        public ReplaceExpressionVisitor(Expression originalExpr, Expression newExpr) {
            this.originalExpr = originalExpr;
            this.newExpr = newExpr;
        }

        @Override
        public void visit(ArrayAccessExpr n, Void arg) {
            super.visit(n, arg);
            if (n.equals(originalExpr)) {
                n.setIndex(((ArrayAccessExpr) newExpr).getIndex());
            }
        }
    }

}

