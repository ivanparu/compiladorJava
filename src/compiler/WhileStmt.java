package compiler;

import java.util.*;
import java.util.Map;

public class WhileStmt implements Stmt {
    private final Expr cond; private final List<Stmt> body;
    public WhileStmt(Expr cond,List<Stmt> body){this.cond=cond;this.body=body;}
    @Override
    public void run(Map<String,Integer> context){
        while(cond.eval(context)!=0)
            for(Stmt s: body) s.run(context);
    }
}
