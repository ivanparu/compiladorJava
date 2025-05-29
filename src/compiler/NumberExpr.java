package compiler;

import java.util.Map;

//Representa un número literal. Al evaluar, simplemente devuelve el valor del número.

public class NumberExpr implements Expr {
    private final int value;                // Valor del número
    public NumberExpr(String lexeme) { 
        value = Integer.parseInt(lexeme);   // Convierte el lexema a un entero
    }

    @Override
    public int eval(Map<String,Integer> context) { return value; }
}
