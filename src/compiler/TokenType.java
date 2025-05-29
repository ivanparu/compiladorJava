package compiler;

import java.util.regex.Pattern;

// Enum que define los tipos de tokens y sus patrones de expresión regular.

public enum TokenType {
    NUMBER("\\d+"),                         // Números
    IDENT("[a-zA-Z_]\\w*"),                 // Identificadores
    OP("(<=|>=|==|!=|[+\\-*/=<>])"),        // Operadores
    PUNC("[();{}]"),                        // Puntuación
    WS("\\s+");                             // Espacios en blanco

    public final Pattern pattern;                 // Patrón regex para el tipo de token

    // Constructor
    TokenType(String regex) { 
        pattern = Pattern.compile("^" + regex);   // Compila el patrón
    }
}
