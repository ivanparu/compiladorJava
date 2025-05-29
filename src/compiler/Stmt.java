package compiler;

import java.util.Map;

public interface Stmt {
    void run(Map<String,Integer> context);
}