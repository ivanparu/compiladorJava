package compiler;

import java.util.*;

/*Realiza el análisis sintáctico, construyendo el árbol de sintaxis abstracta (AST) a partir de la lista de tokens.
 Define cómo se estructuran las declaraciones y expresiones. */

public class Parser {
    private final List<Token> tokens;   // Lista de tokens a analizar
    private int idx = 0;                // Índice actual en la lista de tokens

    //Constructor para el parser.
    public Parser(List<Token> tokens) { this.tokens = tokens; }

    // Método para obtener el siguiente token
    private Token peek() { 
        return idx < tokens.size() ? tokens.get(idx) : null; 
        // Devuelve el token actual o null si llego al final
    }

    // Método para verificar el siguiente token
    private boolean lookaheadIs(String val) {
        int next = idx + 1;
        if (next >= tokens.size()) return false;    // Si no hay más tokens
        Token t = tokens.get(next);
        return t.lexeme.equals(val);                // Verifica si el siguiente token es el esperado
    }

    // Método para consumir un token
    /* consumir el token es cuando se procesa un token y se avanzar en la secuencia de tokens,
     de modo que el lexer o parser no lo considere más en las siguientes operaciones
    */
    private Token eat(TokenType type) {
        Token t = peek();
        if (t != null && t.type == type) {
            idx++;      // Avanza el índice
            return t;   // Retorna el token consumido
        }
        throw new RuntimeException("Expected " + type + " but found " + t); // Manejo de error
    }

    // Método para parsear el programa
    public List<Stmt> parseProgram() {
        List<Stmt> stmts = new ArrayList<>();
        while (peek() != null) stmts.add(parseStmt());  // Parsear sentencias hasta que no haya más tokens
        return stmts;                                   // Retorna la lista de sentencias
    }

      // Método para parsear una sentencia
    private Stmt parseStmt() {
        Token t = peek();
        if      (t.type == TokenType.IDENT && lookaheadIs("=")) return parseAssign();   // Asignación
        else if (t.lexeme.equals("if"))    return parseIf();
        else if (t.lexeme.equals("while")) return parseWhile();
        else if (t.lexeme.equals("print")) return parsePrint();
        throw new RuntimeException("Invalid stmt at " + t);                                 // Manejo de error
    }

    // Método para parsear una asignación
    private AssignStmt parseAssign() {
        String name = eat(TokenType.IDENT).lexeme;          // Nombre de la variable
        eat(TokenType.OP);                                  // Consume el operador '='
        Expr expr = parseExpr();                            // Parsear la expresión
        eat(TokenType.PUNC);                                // Consume el punto y coma ';'
        return new AssignStmt(name, expr);                  // Retorna la sentencia de asignación
    }

    // Método para parsear una sentencia if
    private IfStmt parseIf() {
        eat(TokenType.IDENT);                                                   // Consume 'if'
        eat(TokenType.PUNC);                                                    // Consume '('
        Expr cond = parseExpr();                                                // Parsear la condición
        eat(TokenType.PUNC);                                                    // Consume ')'
        eat(TokenType.PUNC);                                                    // Consume '{'
        List<Stmt> body = new ArrayList<>();
        while (!peek().lexeme.equals("}")) body.add(parseStmt());      // Parsear el cuerpo del if
        eat(TokenType.PUNC);                                                    // Consume '}'
        return new IfStmt(cond, body);                                          // Retorna la sentencia if
    }

    // Método para parsear una sentencia while
    private WhileStmt parseWhile() {
        eat(TokenType.IDENT);                                                   // Consume 'while'
        eat(TokenType.PUNC);                                                    // Consume '('
        Expr cond = parseExpr();                                                // Parsear la condición
        eat(TokenType.PUNC);                                                    // Consume ')'
        eat(TokenType.PUNC);                                                    // Consume '{'
        List<Stmt> body = new ArrayList<>();
        while (!peek().lexeme.equals("}")) body.add(parseStmt());      // Parsear el cuerpo del while
        eat(TokenType.PUNC);                                                    // Consume '}'
        return new WhileStmt(cond, body);                                       // Retorna la sentencia while
    }

    // Método para parsear una sentencia print
    private PrintStmt parsePrint() {
        eat(TokenType.IDENT);                   // Consume 'print'
        eat(TokenType.PUNC);                    // Consume '('
        Expr expr = parseExpr();                // Parsear la expresión a imprimir
        eat(TokenType.PUNC);                    // Consume ')'
        eat(TokenType.PUNC);                    // Consume ';'
        return new PrintStmt(expr);             // Retorna la sentencia print
    }

    // Método para parsear una expresión
    //se encarga de analizar expresiones que pueden incluir tanto operaciones aritméticas (suma y resta) como operaciones de comparación
    private Expr parseExpr() {
        Expr left = parseTerm();                                                                        // Parsear el primer término
        while (peek()!=null && peek().type==TokenType.OP                                        // Mientras haya un token disponible y el token actual sea un operador '+' o '-', 
            && (peek().lexeme.equals("+") || peek().lexeme.equals("-"))) {    // continúa procesando la expresión aritmética.
            String op = eat(TokenType.OP).lexeme;                                                       // Consume el operador
            Expr right = parseTerm();                                                                   // Parsear el siguiente término
            left = new BinOp(left, op, right);                                                          // Crea una nueva operación binaria
        }

        // Manejo de operadores de comparación
        if (peek()!=null && peek().type==TokenType.OP                                            //el if evalua si el token actual es un operador de comparación ((<, >, <=, >=, ==, !=)) y no es null
            && (peek().lexeme.equals("<")  || peek().lexeme.equals(">")  ||
                peek().lexeme.equals("<=") || peek().lexeme.equals(">=") ||
                peek().lexeme.equals("==") || peek().lexeme.equals("!="))) {
            String op = eat(TokenType.OP).lexeme;                                               // Consume el operador
            Expr right = parseTerm();                                                           // Parsear el siguiente término
            left = new BinOp(left, op, right);                                                  // Crea una nueva operación binaria
        }

        return left;                                                                            // Retorna la expresión evaluada
    }

    // Método para parsear un término
    //se encarga de analizar términos en una expresión, específicamente los que pueden incluir multiplicación y división.
    private Expr parseTerm() {
        Expr left = parseFactor();                      // Parsear el primer factor
        while (peek()!=null && peek().type==TokenType.OP && (peek().lexeme.equals("*")||peek().lexeme.equals("/"))) {   //Mientras haya un token, sea un operador y sea especificamente "*" o "/"
            String op = eat(TokenType.OP).lexeme;       // Consume el operador
            Expr right = parseFactor();                 // Parsear el siguiente factor
            left = new BinOp(left, op, right);
        }
        return left;
    }

    // Método para parsear un factor
    private Expr parseFactor() {
        Token t = peek();
        if (t.type == TokenType.NUMBER) {
            eat(TokenType.NUMBER);                      // Consume el número
            return new NumberExpr(t.lexeme);            // Retorna una nueva expresión numérica
        } else if (t.type == TokenType.IDENT) {
            eat(TokenType.IDENT);                       // Consume el identificador
            return new VarExpr(t.lexeme);               // Retorna una nueva expresión de variable
        } else if (t.lexeme.equals("(")) {
            eat(TokenType.PUNC);                        // Consume '('
            Expr e = parseExpr();                       // Parsear la expresión dentro de los paréntesis
            eat(TokenType.PUNC);                        // Consume ')'
            return e;                                   // Retorna la expresión evaluada
        }
        throw new RuntimeException("Unexpected factor " + t);   // Manejo de error
    }
}