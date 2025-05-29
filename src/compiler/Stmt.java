package compiler;

import java.util.Map;

//Interfaz que define el método run, que debe ser implementado por todas las declaraciones.

public interface Stmt {
    void run(Map<String,Integer> context);
}
