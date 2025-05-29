package compiler;

import java.util.*;

public class Parser {
    private final List<Token> tokens;
    private int idx = 0;

    public Parser(List<Token> tokens) { this.tokens = tokens; }

    private Token peek() { return idx < tokens.size() ? tokens.get(idx) : null; }
    private boolean lookaheadIs(String val) {
        int next = idx + 1;
        if (next >= tokens.size()) return false;
        Token t = tokens.get(next);
        return t.lexeme.equals(val);
    }
    private Token eat(TokenType type) {
        Token t = peek();
        if (t != null && t.type == type) {
            idx++;
            return t;
        }
        throw new RuntimeException("Expected " + type + " but found " + t);
    }

    public List<Stmt> parseProgram() {
        List<Stmt> stmts = new ArrayList<>();
        while (peek() != null) stmts.add(parseStmt());
        return stmts;
    }

    private Stmt parseStmt() {
        Token t = peek();
        if      (t.type == TokenType.IDENT && lookaheadIs("=")) return parseAssign();
        else if (t.lexeme.equals("if"))    return parseIf();
        else if (t.lexeme.equals("while")) return parseWhile();
        else if (t.lexeme.equals("print")) return parsePrint();
        throw new RuntimeException("Invalid stmt at " + t);
    }

    private AssignStmt parseAssign() {
        String name = eat(TokenType.IDENT).lexeme;
        eat(TokenType.OP);
        Expr expr = parseExpr();
        eat(TokenType.PUNC); // ;
        return new AssignStmt(name, expr);
    }

    private IfStmt parseIf() {
        eat(TokenType.IDENT); // 'if'
        eat(TokenType.PUNC);   // '('
        Expr cond = parseExpr();
        eat(TokenType.PUNC);   // ')'
        eat(TokenType.PUNC);   // '{'
        List<Stmt> body = new ArrayList<>();
        while (!peek().lexeme.equals("}")) body.add(parseStmt());
        eat(TokenType.PUNC);   // '}'
        return new IfStmt(cond, body);
    }

    private WhileStmt parseWhile() {
        eat(TokenType.IDENT); // 'while'
        eat(TokenType.PUNC);   // '('
        Expr cond = parseExpr();
        eat(TokenType.PUNC);   // ')'
        eat(TokenType.PUNC);   // '{'
        List<Stmt> body = new ArrayList<>();
        while (!peek().lexeme.equals("}")) body.add(parseStmt());
        eat(TokenType.PUNC);   // '}'
        return new WhileStmt(cond, body);
    }

    private PrintStmt parsePrint() {
        eat(TokenType.IDENT); // 'print'
        eat(TokenType.PUNC);   // '('
        Expr expr = parseExpr();
        eat(TokenType.PUNC);   // ')'
        eat(TokenType.PUNC);   // ';'
        return new PrintStmt(expr);
    }

    private Expr parseExpr() {
        Expr left = parseTerm();
        while (peek()!=null && peek().type==TokenType.OP
            && (peek().lexeme.equals("+") || peek().lexeme.equals("-"))) {
            String op = eat(TokenType.OP).lexeme;
            Expr right = parseTerm();
            left = new BinOp(left, op, right);
        }

        if (peek()!=null && peek().type==TokenType.OP
            && (peek().lexeme.equals("<")  || peek().lexeme.equals(">")  ||
                peek().lexeme.equals("<=") || peek().lexeme.equals(">=") ||
                peek().lexeme.equals("==") || peek().lexeme.equals("!="))) {
            String op = eat(TokenType.OP).lexeme;
            Expr right = parseTerm();
            left = new BinOp(left, op, right);
        }

        return left;
    }

    private Expr parseTerm() {
        Expr left = parseFactor();
        while (peek()!=null && peek().type==TokenType.OP && (peek().lexeme.equals("*")||peek().lexeme.equals("/"))) {
            String op = eat(TokenType.OP).lexeme;
            Expr right = parseFactor();
            left = new BinOp(left, op, right);
        }
        return left;
    }

    private Expr parseFactor() {
        Token t = peek();
        if (t.type == TokenType.NUMBER) {
            eat(TokenType.NUMBER);
            return new NumberExpr(t.lexeme);
        } else if (t.type == TokenType.IDENT) {
            eat(TokenType.IDENT);
            return new VarExpr(t.lexeme);
        } else if (t.lexeme.equals("(")) {
            eat(TokenType.PUNC);
            Expr e = parseExpr();
            eat(TokenType.PUNC);
            return e;
        }
        throw new RuntimeException("Unexpected factor " + t);
    }
}