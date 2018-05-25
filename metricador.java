import java.util.HashMap;
import java.util.Map;

public class metricador extends Java8BaseListener{
    static int contadorFunciones = 0;
    static int contadorLocales = 0;
    int N1, N2, n1, n2 = 0;
    Map <String, Integer> table = new HashMap<String, Integer>();


    @Override public void enterLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx) { 
        contadorLocales++;
    }
    @Override public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        contadorFunciones++;
        System.out.println("# Variables locales: " + contadorLocales);
        contadorLocales = 0;
        System.out.println("N1: " + N1);
        System.out.println("N2: " + N2);
        System.out.println("n1: " + n1);
        System.out.println("n2: " + n2);        
        System.out.println(table);
     }
     @Override public void exitCompilationUnit(Java8Parser.CompilationUnitContext ctx) { 
         System.out.println("# Funciones: " + contadorFunciones);
     }
    
     /* Operacionaes de tipo A -> a = b */
     @Override public void enterVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
        System.out.println("contexto declarator: " + ctx.getText());      
        
         if (ctx.getText().indexOf('=') != -1) {            
            if (!table.containsKey("=")) {                
                table.put("=", 1);
                n1++;
                N1++;                
            } else {                
                int num_ocurrences = table.get("=");
                table.put("=",++num_ocurrences);
                N1++;                
            }
         }
      }
      /*operaciones de adicion a + b y a - b*/
      @Override public void enterAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx) { 
        if((ctx.getText().indexOf('+') != -1)){
            if (!table.containsKey("+")){
                table.put("+", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("+");
                table.put("+",++num_ocurrences);
                N1++;
            }
        }
            /*para el menos -*/
        if(ctx.getText().indexOf('-') != -1 ){
            if(!table.containsKey("-")){
                table.put("-", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("-");
                table.put("-", ++num_ocurrences);
                N1++;
            }
        }
      }

    /* operaciones * / % */
    @Override public void enterMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx) {
        if((ctx.getText().indexOf('*') != -1)){
            if (!table.containsKey("*")){
                table.put("*", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("*");
                table.put("*",++num_ocurrences);
                N1++;
            }
        }
        if(ctx.getText().indexOf('/') != -1 ){
            if(!table.containsKey("/")){
                table.put("/", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("/");
                table.put("/", ++num_ocurrences);
                N1++;
            }
        }
        if(ctx.getText().indexOf('%') != -1 ){
            if(!table.containsKey("%")){
                table.put("%", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("%");
                table.put("%", ++num_ocurrences);
                N1++;
            }
        }
     }
     /*Is statement */
     @Override public void enterIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
         System.out.println(ctx.getText());
         if (ctx.getText().indexOf("if") != -1) {
             if (!table.containsKey("if")) {
                 table.put("if", 1);
                 n1++;
                 N1++;
             } else {
                int num_ocurrences = table.get("if");
                table.put("if", ++num_ocurrences);
                N1++;
             }
         }

         if (ctx.getText().indexOf("else") != -1) {
            if (!table.containsKey("else")) {
                table.put("else", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("else");
               table.put("else", ++num_ocurrences);
               N1++;
            }
        }
      }
      /**Switch (solo switch, sin instrucciones) */
      @Override public void enterSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
        if (ctx.getText().indexOf("switch") != -1) {
            if (!table.containsKey("switch")) {
                table.put("switch", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("switch");
               table.put("switch", ++num_ocurrences);
               N1++;
            }
        }
    } 
    /**Encuentra case y default*/
    @Override public void enterSwitchLabel(Java8Parser.SwitchLabelContext ctx) {
        System.out.println(ctx.getText());
        if (ctx.getText().indexOf("switch") != -1) {
            if (!table.containsKey("case")) {
                table.put("case", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("case");
               table.put("case", ++num_ocurrences);
               N1++;
            }
        }
        if (ctx.getText().indexOf("default") != -1) {
            if (!table.containsKey("default")) {
                table.put("default", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("default");
               table.put("default", ++num_ocurrences);
               N1++;
            }
        }
    }
    /**Encuentra break */
    @Override public void enterBreakStatement(Java8Parser.BreakStatementContext ctx) {
        System.out.println(ctx.getText());
        if (ctx.getText().indexOf("break") != -1) {
            if (!table.containsKey("break")) {
                table.put("break", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("break");
               table.put("break", ++num_ocurrences);
               N1++;
            }
        }
    }

    @Override public void enterForStatement(Java8Parser.ForStatementContext ctx) {
        System.out.println("for: " + ctx.getText());
        System.out.println(ctx.getText());
        if (ctx.getText().indexOf("for") != -1) {
            if (!table.containsKey("for")) {
                table.put("for", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("for");
               table.put("for", ++num_ocurrences);
               N1++;
            }
        }
    }
    /**Continue statement */
    @Override public void enterContinueStatement(Java8Parser.ContinueStatementContext ctx) {
        System.out.println(ctx.getText());
        if (ctx.getText().indexOf("continue") != -1) {
            if (!table.containsKey("continue")) {
                table.put("continue", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("continue");
               table.put("continue", ++num_ocurrences);
               N1++;
            }
        }
    }
    /**Statement do-while */
    @Override public void enterDoStatement(Java8Parser.DoStatementContext ctx) {
        if (ctx.getText().indexOf("do") != -1) {
            if (!table.containsKey("do")) {
                table.put("do", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("do");
               table.put("do", ++num_ocurrences);
               N1++;
            }
        }
        if (ctx.getText().indexOf("while") != -1) {
            if (!table.containsKey("while")) {
                table.put("while", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("while");
               table.put("while", ++num_ocurrences);
               N1++;
            }
        }
    }
    /**While statement */
    @Override public void enterWhileStatement(Java8Parser.WhileStatementContext ctx) {
        if (ctx.getText().indexOf("while") != -1) {
            if (!table.containsKey("while")) {
                table.put("while", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("while");
               table.put("while", ++num_ocurrences);
               N1++;
            }
            
        }
    }
    /**TRue and False values in boolean type variables */
    @Override public void enterLiteral(Java8Parser.LiteralContext ctx) {
        if (ctx.getText().indexOf("true") != -1) {
            if (!table.containsKey("true")) {
                table.put("true", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("true");
               table.put("true", ++num_ocurrences);
               N1++;
            }            
        }
        if (ctx.getText().indexOf("false") != -1) {
            if (!table.containsKey("false")) {
                table.put("false", 1);
                n1++;
                N1++;
            } else {
               int num_ocurrences = table.get("false");
               table.put("false", ++num_ocurrences);
               N1++;
            }            
        }
    }
}
