package compiler;

import java.util.Map;

// Implementa la instrucción print. Evalúa la expresión y la imprime en la consola.

public class PrintStmt implements Stmt {
    private final Expr expr;
    public PrintStmt(Expr expr){ this.expr = expr; }
    @Override
    public void run(Map<String,Integer> context) {
        System.out.println(expr.eval(context));
    }
}
