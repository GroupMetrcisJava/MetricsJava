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

     /* operacion and */
     @Override public void enterConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx) { 
        if((ctx.getText().indexOf("&&") != -1)){
            if (!table.containsKey("&&")){
                table.put("&&", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("&&");
                table.put("&&",++num_ocurrences);
                N1++;
            }
        }
     }

     /*operacion or*/
     @Override public void enterConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx) { 
        if((ctx.getText().indexOf("||") != -1)){
            if (!table.containsKey("||")){
                table.put("||", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("||");
                table.put("||",++num_ocurrences);
                N1++;
            }
        }
     }

    /* operacion not ! */
     @Override public void enterUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx) { 
        if((ctx.getText().indexOf('!') != -1)){
            if (!table.containsKey("!")){
                table.put("!", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("!");
                table.put("!",++num_ocurrences);
                N1++;
            }
        }
     }
     /* operacion diferente != */
     @Override public void enterEqualityExpression(Java8Parser.EqualityExpressionContext ctx) { 
        if((ctx.getText().indexOf("!=") != -1)){
            if (!table.containsKey("!=")){
                table.put("!=", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("!=");
                table.put("!=",++num_ocurrences);
                N1++;
            }
        }
     }

     /* operador %= */
     @Override public void enterAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx) { 
        if((ctx.getText().indexOf("%=") != -1)){
            if (!table.containsKey("%=")){
                table.put("%=", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("%=");
                table.put("%=",++num_ocurrences);
                N1++;
            }
        }
     }

     /* operador bitwise & */
     @Override public void enterAndExpression(Java8Parser.AndExpressionContext ctx) { 
        if((ctx.getText().indexOf('&') != -1)){
            if (!table.containsKey("&")){
                table.put("&", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("&");
                table.put("&",++num_ocurrences);
                N1++;
            }
        }
     }
    
    
      
}
