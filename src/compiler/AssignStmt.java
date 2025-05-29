package compiler;

import java.util.Map;

public class AssignStmt implements Stmt {
    private final String name; // Nombre de la variable
    private final Expr expr;   // Expresión a evaluar
    
    public AssignStmt(String name,Expr expr){
        this.name=name;
        this.expr=expr;
    }

    @Override
    public void run(Map<String,Integer> context){ context.put(name, expr.eval(context)); }
}

/*
Implementa la asignación de una expresión a una variable.
Al ejecutar, evalúa la expresión y almacena el resultado en el contexto (un mapa de variables).
*/