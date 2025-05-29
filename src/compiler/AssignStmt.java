package compiler;

import java.util.Map;

public class AssignStmt implements Stmt {
    private final String name; private final Expr expr;
    public AssignStmt(String name,Expr expr){this.name=name;this.expr=expr;}
    @Override
    public void run(Map<String,Integer> context){ context.put(name, expr.eval(context)); }
}