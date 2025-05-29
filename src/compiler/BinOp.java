package compiler;

import java.util.Map;

public class BinOp implements Expr {
    private final Expr left,right;
    private final String op;
    public BinOp(Expr left,String op,Expr right) { this.left=left;this.op=op;this.right=right; }
    @Override
    public int eval(Map<String,Integer> context) {
        int l = left.eval(context);
        int r = right.eval(context);
        return switch (op) {
            case "+"  -> l + r;
            case "-"  -> l - r;
            case "*"  -> l * r;
            case "/"  -> l / r;
            case "<"  -> (l < r)  ? 1 : 0;
            case ">"  -> (l > r)  ? 1 : 0;
            case "<=" -> (l <= r) ? 1 : 0;
            case ">=" -> (l >= r) ? 1 : 0;
            case "==" -> (l == r) ? 1 : 0;
            case "!=" -> (l != r) ? 1 : 0;
            default   -> throw new RuntimeException("Invalid op " + op);
        };
    }
}