package compiler;

import java.util.*;
import java.util.regex.*;

public class Lexer {
    private final String input;
    private int pos = 0;
    private static final TokenType[] TYPES = TokenType.values();

    public Lexer(String input) { this.input = input; }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            boolean matched = false;
            String rem = input.substring(pos);
            for (TokenType tt : TYPES) {
                Matcher m = tt.pattern.matcher(rem);
                if (m.find()) {
                    String lex = m.group();
                    pos += lex.length();
                    matched = true;
                    if (tt != TokenType.WS) tokens.add(new Token(tt, lex));
                    break;
                }
            }
            if (!matched) throw new RuntimeException("Unexpected char: " + input.charAt(pos));
        }
        return tokens;
    }
}