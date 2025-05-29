package compiler;

import java.util.regex.Pattern;

public enum TokenType {
    NUMBER("\\d+"),
    IDENT("[a-zA-Z_]\\w*"),
    OP("(<=|>=|==|!=|[+\\-*/=<>])"),
    PUNC("[();{}]"),
    WS("\\s+");

    public final Pattern pattern;
    TokenType(String regex) { pattern = Pattern.compile("^" + regex); }
}