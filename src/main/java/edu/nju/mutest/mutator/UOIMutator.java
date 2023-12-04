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
                        if (expr instanceof BinaryExpr || expr instanceof NameExpr || expr instanceof MethodCallExpr || expr instanceof BooleanLiteralExpr) {
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
                if (isOperatorApplicable(expr, op)) {
                    UnaryExpr unaryExpr = new UnaryExpr(expr.clone(), op);
                    // 特殊处理逻辑非操作符
                    if (op == UnaryExpr.Operator.LOGICAL_COMPLEMENT && needsParentheses(expr)) {
                        unaryExpr = new UnaryExpr(new EnclosedExpr(expr.clone()), op);
                    }
                    mutants.add(insertUnaryExpr(unaryExpr, expr));
                }
            }
        }
        return mutants;
    }

    private boolean isBooleanExpression(Expression expr) {
        if (expr instanceof BinaryExpr) {
            BinaryExpr binaryExpr = (BinaryExpr) expr;
            switch (binaryExpr.getOperator()) {
                case EQUALS:
                case NOT_EQUALS:
                case LESS:
                case LESS_EQUALS:
                case GREATER:
                case GREATER_EQUALS:
                case OR:
                case AND:
                    return true;
                default:
                    return false;
            }
        } else if (expr instanceof UnaryExpr) {
            UnaryExpr unaryExpr = (UnaryExpr) expr;
            return unaryExpr.getOperator() == UnaryExpr.Operator.LOGICAL_COMPLEMENT;
        } else if (expr instanceof MethodCallExpr || expr instanceof BooleanLiteralExpr) {
            return true;
        }
        return false;
    }


    private boolean isOperatorApplicable(Expression expr, UnaryExpr.Operator op) {
        // 基于表达式的类型和结构来决定是否适用操作符
        switch (op) {
            case PREFIX_INCREMENT:
            case PREFIX_DECREMENT:
                // 增加和减少运算符通常适用于变量（例如，NameExpr）
                return expr instanceof NameExpr || expr instanceof FieldAccessExpr;

            case LOGICAL_COMPLEMENT:
                // 确保表达式是有效的布尔表达式
                return isBooleanExpression(expr);

            case PLUS:
            case MINUS:
            case BITWISE_COMPLEMENT:
                // 通常适用于数字类型
                // 可以根据表达式的类型进行更精确的检查
                return expr instanceof LiteralExpr || expr instanceof BinaryExpr ||
                        expr instanceof UnaryExpr || expr instanceof NameExpr ||
                        expr instanceof MethodCallExpr || expr instanceof FieldAccessExpr;

            default:
                return false;
        }
    }

    private boolean needsParentheses(Expression expr) {
        // 判断是否需要括号包围表达式
        return !(expr instanceof EnclosedExpr || expr instanceof BooleanLiteralExpr || expr instanceof NameExpr);
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
