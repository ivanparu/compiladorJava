package compiler;

// Representa un token, que tiene un tipo y un lexema.
// un lexema es un elemento del lenguaje (variable, operador, número y/o delimitador("()","{}","[]",";")).

public class Token {
    public final TokenType type;    // Tipo de token
    public final String lexeme;     // Lexema del token

    // Constructor
    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    // Representación en cadena del token
    @Override
    public String toString() {
        return type + ":'" + lexeme + "'";
    }
}
