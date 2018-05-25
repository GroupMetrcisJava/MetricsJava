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

      @Override public void enterSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
        System.out.println(ctx.getText());
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
    
    
      
}
