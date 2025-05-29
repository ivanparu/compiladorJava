package compiler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/*Clase principal que ejecuta el programa. 
Lee el archivo fuente, tokeniza el contenido, lo analiza y ejecuta las instrucciones.
*/

public class Interpreter {
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "prog.mini";
        String src  = Files.readString(Path.of(file));

        List<Token> tokens    = new Lexer(src).tokenize();
        List<Stmt>   program   = new Parser(tokens).parseProgram();
        Map<String,Integer> ctx = new HashMap<>();

        for (Stmt s : program) s.run(ctx);
    }
}
