package compiler;

import java.util.*;

//Implementa la estructura de bucle while. Evalúa la condición y ejecuta el cuerpo mientras la condición sea verdadera.

public class WhileStmt implements Stmt {
    private final Expr cond;                            // Condición del while
    private final List<Stmt> body;                      // Cuerpo de la sentencia
    
    // Constructor
    public WhileStmt(Expr cond,List<Stmt> body){
        this.cond=cond;this.body=body;                  // Inicializa la condición y el cuerpo de la sentencia
    }

    @Override
    public void run(Map<String,Integer> context){
        while(cond.eval(context)!=0)
            for(Stmt s: body) s.run(context);
    }
}
