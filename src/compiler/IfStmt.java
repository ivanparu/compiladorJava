package compiler;

import java.util.*;

//Implementa la estructura condicional if. Evalúa la condición y ejecuta el cuerpo si la condición es verdadera.

public class IfStmt implements Stmt {
    private final Expr cond;            // Condición a evaluar
    private final List<Stmt> body;      // Cuerpo de la declaración

    //Constructor para la declaración 'if'.
    public IfStmt(Expr cond,List<Stmt> body){this.cond=cond;this.body=body;}

    //Ejecuta la declaración 'if'.
    @Override
    public void run(Map<String,Integer> context){
        if(cond.eval(context)!=0)
            for(Stmt s: body) s.run(context);
    }
}
