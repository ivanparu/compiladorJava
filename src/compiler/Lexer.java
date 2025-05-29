package compiler;

import java.util.*;
import java.util.regex.*;

/* Realiza el análisis léxico, convirtiendo el texto fuente en una lista de tokens.
Utiliza expresiones regulares para identificar diferentes tipos de tokens.
LEXER = analizador léxico
*/

public class Lexer {
    private final String input;                                     // Código fuente a analizar
    private int pos = 0;                                            // Posición actual en el código fuente
    private static final TokenType[] TYPES = TokenType.values();    // Tipos de tokens

    //Constructor para el lexer.
    public Lexer(String input) { this.input = input; }

    //Tokeniza el código fuente y devuelve una lista de tokens.
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            boolean matched = false;                    // Indica si se encontró un token
            String rem = input.substring(pos);          // Subcadena restante
            for (TokenType tt : TYPES) {
                Matcher m = tt.pattern.matcher(rem);    // Busca el patrón del token
                if (m.find()) {
                    String lex = m.group();             // Lexema encontrado
                    pos += lex.length();                // Avanza la posición
                    matched = true;
                    if (tt != TokenType.WS) tokens.add(new Token(tt, lex));     // Agrega el token si no es un espacio en blanco
                    break;
                }
            }
            // Manejo de caracteres inesperados
            if (!matched) throw new RuntimeException("Unexpected char: " + input.charAt(pos));
        }
        return tokens; // Devuelve la lista de tokens
    }
}
