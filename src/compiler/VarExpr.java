package compiler;

import java.util.Map;

//Representa una expresión de variable. Al evaluar, busca el valor de la variable en el contexto.

public class VarExpr implements Expr {
    private final String name;                              // Nombre de la variable
    public VarExpr(String name) { this.name = name; }       // Inicializa el nombre de la variable

    @Override                                               // Evalúa la expresión de variable
    public int eval(Map<String,Integer> context) { 
        return context.getOrDefault(name,0);   // Retorna el valor de la variable o 0 si no existe
    }
} 
