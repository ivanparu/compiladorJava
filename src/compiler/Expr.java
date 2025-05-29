package compiler;

import java.util.Map;

public interface Expr {
    int eval(Map<String,Integer> context);
}