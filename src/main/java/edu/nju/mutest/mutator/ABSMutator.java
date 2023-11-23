package edu.nju.mutest.mutator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.BinaryExpr;
import edu.nju.mutest.visitor.collector.BinaryExprCollector;

import java.util.List;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.*;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.DIVIDE;

public class ABSMutator extends AbstractMutator{
    private List<BinaryExpr> mutPoints = null;
    private List<CompilationUnit> mutants = new NodeList<>();

    public ABSMutator(CompilationUnit cu) {
        super(cu);
    }

    public List<CompilationUnit> mutate(){
        return null;
    }

    @Override
    public void locateMutationPoints() {

    }



}
