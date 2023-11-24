package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.nju.mutest.visitor.collector.ExpressionStmtCollector;
import edu.nju.mutest.visitor.collector.MethodDeclarationCollector;

import java.util.ArrayList;
import java.util.List;

public class UOIMutator extends AbstractMutator {

    private List<Expression> mutPoints;
    private List<CompilationUnit> mutants;

    private UnaryExpr.Operator[] uoiOperators = {
        UnaryExpr.Operator.PLUS, 
        UnaryExpr.Operator.MINUS, 
        UnaryExpr.Operator.LOGICAL_COMPLEMENT, 
        UnaryExpr.Operator.BITWISE_COMPLEMENT, 
        UnaryExpr.Operator.PREFIX_INCREMENT, 
        UnaryExpr.Operator.PREFIX_DECREMENT
    };

    public UOIMutator(CompilationUnit cu) {
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
                public void visit(ReturnStmt n, Void arg) {
                    super.visit(n, arg);
                    Expression expr = n.getExpression().orElse(null);
                    if (expr != null) {
                        // 检查表达式是否适合一元操作符插入
                        if (expr instanceof BinaryExpr || expr instanceof NameExpr || expr instanceof MethodCallExpr) {
                            mutPoints.add(expr);
                        }
                    }
                }
            }, null);
        }
    }


    @Override
    public List<CompilationUnit> mutate() {
        if (mutPoints.isEmpty())
            throw new RuntimeException("没有找到适合变异的点！");

        for (Expression expr : mutPoints) {
            for (UnaryExpr.Operator op : uoiOperators) {
                // 创建新的一元表达式
                UnaryExpr unaryExpr = new UnaryExpr(expr.clone(), op);
                // 插入一元表达式并添加到变异体列表中
                mutants.add(insertUnaryExpr(unaryExpr, expr));
            }
        }
        return mutants;
    }

    private CompilationUnit insertUnaryExpr(UnaryExpr unaryExpr, Expression originalExpr) {
        // 克隆原始 CompilationUnit
        CompilationUnit mutatedCU = this.origCU.clone();
        // 使用访问者模式替换原始表达式为一元表达式
        mutatedCU.accept(new ReplaceExpressionVisitor(originalExpr, unaryExpr), null);
        return mutatedCU;
    }

    private static class ReplaceExpressionVisitor extends VoidVisitorAdapter<Void> {
        private final Expression originalExpr;
        private final UnaryExpr newExpr;

        public ReplaceExpressionVisitor(Expression originalExpr, UnaryExpr newExpr) {
            this.originalExpr = originalExpr;
            this.newExpr = newExpr;
        }

        @Override
        public void visit(ReturnStmt n, Void arg) {
            super.visit(n, arg);
            n.getExpression().ifPresent(expr -> {
                if (expr.equals(originalExpr)) {
                    n.setExpression(newExpr);
                }
            });
        }

        // 保留原有的 visit 方法，以防有其他 ExpressionStmt 类型的节点
        @Override
        public void visit(ExpressionStmt n, Void arg) {
            super.visit(n, arg);
            if (n.getExpression().equals(originalExpr)) {
                n.setExpression(newExpr);
            }
        }
    }

}
