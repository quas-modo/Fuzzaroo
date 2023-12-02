package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sun.source.tree.UnaryTree;
import edu.nju.mutest.visitor.collector.BinaryExprCollector;
import edu.nju.mutest.visitor.collector.ExpressionStmtCollector;
import edu.nju.mutest.visitor.collector.MethodDeclarationCollector;
import edu.nju.mutest.visitor.collector.cond.CollectionCond;
import edu.nju.mutest.visitor.collector.cond.NumericLiteralCond;

import java.util.ArrayList;
import java.util.List;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.DIVIDE;

public class ABSMutator extends AbstractMutator{
    private List<Expression> mutPoints;
    private List<CompilationUnit> mutants;

    private UnaryExpr.Operator[] unaryTargetOps = {
            UnaryExpr.Operator.PLUS, UnaryExpr.Operator.MINUS
    };

    public ABSMutator(CompilationUnit cu) {
        super(cu);
        mutPoints = new ArrayList<>();
        mutants = new NodeList<>();
    }

    public List<CompilationUnit> mutate(){
        if(this.mutPoints == null){
            System.out.println("There is no mutPoints in ABSMutator");
            System.exit(1);
        }

        for(Expression expr: mutPoints){
            if(expr instanceof LiteralExpr){
                // 适用于int或者double的转换
                for(UnaryExpr.Operator targetOp: unaryTargetOps){
                    UnaryExpr targetExpr = new UnaryExpr(expr, targetOp);
                    mutants.add(insertUnaryExpr(targetExpr, expr));
                }

            }else if(expr instanceof UnaryExpr){
                // 适用于 c = -2, 或者 a = -c + 2这类表达式
                UnaryExpr.Operator origOp = ((UnaryExpr) expr).getOperator();
                for(UnaryExpr.Operator targetOp : unaryTargetOps){
                    if (origOp.equals(targetOp)) continue;
                    UnaryExpr targetExpr = new UnaryExpr(((UnaryExpr) expr).getExpression(), targetOp);
                    mutants.add(insertUnaryExpr(targetExpr, expr));
                }
            }else if (expr instanceof BinaryExpr){
                // 适用于 a + b 的整体转换
                for(UnaryExpr.Operator targetOp: unaryTargetOps){
                    EnclosedExpr enclosedExpr = new EnclosedExpr(expr);
                    UnaryExpr targetExpr= new UnaryExpr(enclosedExpr, targetOp);
                    mutants.add(insertUnaryExpr(targetExpr, expr));
                }
            }
            IntegerLiteralExpr zeroExpr = new IntegerLiteralExpr(0);
            mutants.add(insertUnaryExpr(new UnaryExpr(zeroExpr, UnaryExpr.Operator.PLUS), expr)); // 虽然都变成+0了，但感觉也是正确的
        }
        return this.mutants;
    }

    @Override
    public void locateMutationPoints() {
        List<MethodDeclaration> methods = MethodDeclarationCollector.collect(this.origCU);
        for(MethodDeclaration method: methods){
            List<ExpressionStmt> exprStmts = new ArrayList<>();
            ExpressionStmtCollector.collect(method, exprStmts);
            for(ExpressionStmt exprStmt: exprStmts){
                Expression expr = exprStmt.getExpression();
                collectPoints(expr);
            }
        }
    }

    private void collectPoints(Node node){
        if(node == null) {
            return;
        }else if(node instanceof BinaryExpr || node instanceof LiteralExpr || node instanceof UnaryExpr){


            if(node instanceof UnaryExpr){
                if(((UnaryExpr) node).getOperator() == UnaryExpr.Operator.PLUS
                || ((UnaryExpr) node).getOperator() == UnaryExpr.Operator.MINUS){
                    this.mutPoints.add(((UnaryExpr) node).clone());
                }
            }else{
                this.mutPoints.add(((Expression) node).clone());
            }


        }
        List<Node> children = node.getChildNodes();
        for(Node child: children){
            collectPoints(child);
        }
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


        // 保留原有的 visit 方法，以防有其他 ExpressionStmt 类型的节点
        @Override
        public void visit(ExpressionStmt n, Void arg) {
            super.visit(n, arg);
            if (n.getExpression().equals(originalExpr)) {
                n.setExpression(newExpr);
            }
        }

        @Override
        public void visit(UnaryExpr n, Void arg){
            super.visit(n, arg);
            if(n.equals(originalExpr)){ //todo:存在一些会把所有visit到的一样的数值都改掉的bug
                n.setOperator(newExpr.getOperator());
            }
        }

        @Override
        public void visit(IntegerLiteralExpr n, Void arg){
            super.visit(n, arg);
            if(n.equals(originalExpr)){
                n.replace(newExpr);
            }
        }

        @Override
        public void visit(DoubleLiteralExpr n, Void arg){
            super.visit(n, arg);
            if(n.equals(originalExpr)){
                n.replace(newExpr);
            }
        }

        @Override
        public void visit(BinaryExpr n, Void arg){
            super.visit(n, arg);
            if(n.equals(originalExpr)){
                n.replace(newExpr);
            }
        }
    }
}
