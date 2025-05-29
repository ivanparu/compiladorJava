package compiler;

import java.util.Map;

/*Representa operaciones binarias (suma, resta, multiplicación, división, y comparaciones).
Evalúa las expresiones de la izquierda y derecha y aplica la operación correspondiente.
 */

public class BinOp implements Expr {
    private final Expr left,right; //operandos
    private final String op;       //operador

    //Constructor para la operación binaria.
    public BinOp(Expr left,String op,Expr right) { this.left=left;this.op=op;this.right=right; }

    //evalua la operación binaria
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
