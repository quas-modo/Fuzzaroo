package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.LiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
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

        }
        return null;
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
        }else if(node instanceof BinaryExpr || node instanceof LiteralExpr){
            this.mutPoints.add(((Expression) node).clone());
        }
        List<Node> children = node.getChildNodes();
        for(Node child: children){
            collectPoints(child);
        }
    }



}
