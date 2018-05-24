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
         String[] aux = ctx.getText().split("");         
         if (aux.length > 1 && aux[1].equals("=")) {            
            if (!table.containsKey(aux[1])) {                
                table.put(aux[1], 1);
                n1++;
                N1++;                
            } else {                
                int num_ocurrences = table.get(aux[1]);
                table.put(aux[1],++num_ocurrences);
                N1++;                
            }
         }
      }
      /* Operacionaes de tipo A -> a + b, A -> a - b*/
      @Override public void enterAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx) {
          System.out.println(ctx.getText());
          String[] aux = ctx.getText().split("");
          if (aux.length > 1 ) {
              if (!table.containsKey(aux[1])) {
                table.put(aux[1], 1);
                N1++;
                n1++;
              } else {
                int num_ocurrences = table.get(aux[1]);
                table.put(aux[1], ++num_ocurrences);
                N1++;
            }
          }
       }
       /*Expresiones A -> a * b, A -> a / b, A-> a % b  */
       @Override public void enterMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx) {
            System.out.println(ctx.getText());
            String[] aux = ctx.getText().split("");
            if (aux.length > 1) {
                if (!table.containsKey(aux[1])) {
                    table.put(aux[1], 1);
                    N1++;
                    n1++;
                  } else {
                    int num_ocurrences = table.get(aux[1]);
                    table.put(aux[1], ++num_ocurrences);
                    N1++;
                }
            }
        }

        /*Expresiones de tipo A -> + a, A -> - b */
        @Override public void enterUnaryExpression(Java8Parser.UnaryExpressionContext ctx) {
            System.out.println(ctx.getText());
            String[] aux = ctx.getText().split("");
            if (aux.length > 1) {
                if (!table.containsKey(aux[0])) {
                    table.put(aux[0], 1);
                    N1++;
                    n1++;
                  } else {
                    int num_ocurrences = table.get(aux[0]);
                    table.put(aux[0], ++num_ocurrences);
                    N1++;
                }
            }
        }
        /*Expresiones de tipo A -> ++ a */
        @Override public void enterPreIncrementExpression(Java8Parser.PreIncrementExpressionContext ctx) {
            System.out.println(ctx.getText());
            String[] aux = ctx.getText().split("");
            if (aux.length > 1) {
                if (!table.containsKey(aux[0])) {
                    table.put(aux[0], 1);
                    N1++;
                    n1++;
                  } else {
                    int num_ocurrences = table.get(aux[0]);
                    table.put(aux[0], ++num_ocurrences);
                    N1++;
                }
            }
        }
        /*Expresiones de tipo A -> -- a */
        @Override public void enterPreDecrementExpression(Java8Parser.PreDecrementExpressionContext ctx) {
            System.out.println(ctx.getText());
            String[] aux = ctx.getText().split("");
            if (aux.length > 1) {
                if (!table.containsKey(aux[0])) {
                    table.put(aux[0], 1);
                    N1++;
                    n1++;
                  } else {
                    int num_ocurrences = table.get(aux[0]);
                    table.put(aux[0], ++num_ocurrences);
                    N1++;
                }
            }
        }
        /*Expresiones de tipo A -> !a, A -> ~a */
        @Override public void enterUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx) {
            System.out.println(ctx.getText());
            String[] aux = ctx.getText().split("");
            if (aux.length > 1) {
                if (!table.containsKey(aux[0])) {
                    table.put(aux[0], 1);
                    N1++;
                    n1++;
                  } else {
                    int num_ocurrences = table.get(aux[0]);
                    table.put(aux[0], ++num_ocurrences);
                    N1++;
                }
            }
        }

        @Override public void enterPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) {
            System.out.println(ctx.getText());
            String[] aux = ctx.getText().split("");
            if (aux.length > 1) {
                if (!table.containsKey(aux[1])) {
                    table.put(aux[1], 1);
                    N1++;
                    n1++;
                  } else {
                    int num_ocurrences = table.get(aux[1]);
                    table.put(aux[0], ++num_ocurrences);
                    N1++;
                }
            }
         }

}
