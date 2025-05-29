package compiler;

import java.util.Map;

public class NumberExpr implements Expr {
    private final int value;
    public NumberExpr(String lexeme) { value = Integer.parseInt(lexeme); }
    @Override
    public int eval(Map<String,Integer> context) { return value; }
}