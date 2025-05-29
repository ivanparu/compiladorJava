package compiler;

import java.util.Map;

//Interfaz que define una expresión en el lenguaje.
public interface Expr {
    //define el método eval, que debe ser implementado por todas las expresiones.
    int eval(Map<String,Integer> context);
    //Evalúa la expresión y devuelve un valor entero.
}

