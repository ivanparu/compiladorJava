package compiler;

import java.util.Map;

public class VarExpr implements Expr {
    private final String name;
    public VarExpr(String name) { this.name = name; }
    @Override
    public int eval(Map<String,Integer> context) { return context.getOrDefault(name,0); }
} 