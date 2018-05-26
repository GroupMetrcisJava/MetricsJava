import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Arrays;
import java.math.*;

public class metricador extends Java8BaseListener{
    static int contadorFunciones = 0;
    static int contadorLocales = 0;
    static int contadorImports = 0;
    static int contadorClases = 0;
    static int contadorArrays = 0;
    static int contadorTiposDato = 0;
    static int contadorIds = 0;
    
    int N1, N2, n1, n2 = 0;
    Map <String, Integer> table = new HashMap<String, Integer>();
    Map <String, Integer> datatypes = new HashMap<String, Integer>();
    Map <String, Integer> methodtypes = new HashMap<String, Integer>();


    @Override public void enterLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx) { 
        contadorLocales++;
    }
    @Override public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        contadorFunciones++;
        System.out.println("# Variables locales: " + contadorLocales);
        contadorLocales = 0;   
        System.out.println("tabla: "+ table);
        System.out.println("tipos dato: "+datatypes);
        System.out.println("HALSTEAD MEDIDAS: ------");
        System.out.println("N1: " + N1);
        System.out.println("N2: " + N2);
        System.out.println("n1: " + n1);
        System.out.println("n2: " + n2);
        int N = N1 + N2;
        double V = N * (Math.log(n1+n2) / (Math.log(2)));  
        double L = (2.0*n2) / (n1*N2);
        double E = (n1+N2 * (N1+N2)* (Math.log(n1+n2) / (Math.log(2))) ) / (2*n2);    
        System.out.println("Longitud del programa: "+N);
        System.out.println("Volumen del programa: "+V);
        System.out.println("Nivel de especificacion de abtraccion: "+L);
        System.out.println("Esfuerzo del programa: "+E);

     }
     @Override public void exitCompilationUnit(Java8Parser.CompilationUnitContext ctx) { 
         System.out.println("# Funciones: " + contadorFunciones);
         System.out.println("# Imports:  "+ contadorImports);
         System.out.println("# Clases: "+ contadorClases);
         System.out.println("# Arreglos: "+ contadorArrays);
         
     }
    
     /* Operacionaes de tipo A -> a = b */
     @Override public void enterVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
        //System.out.println("contexto declarator: " + ctx.getText());      
        
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
      /*operaciones de adicion a + b y a - b
        Last index of, comparar cual de los dos encuentra primero e irse por ese camino
      */
      @Override public void enterAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx) { 
        //System.out.println("additive: " + ctx.getText());
        String first, context = ctx.getText();
        Integer[] possibles = {context.lastIndexOf("+"), context.lastIndexOf("-")};
        if (ctx.getText().lastIndexOf("+") == Collections.max(Arrays.asList(possibles))) {
            first = "+";
        } else {
            first = "-";
        }
        if((ctx.getText().lastIndexOf(first) != -1)){
            if (!table.containsKey(first)){
                table.put(first, 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get(first);
                table.put(first, ++num_ocurrences);
                N1++;
            }            
        }        
    }

    /* operaciones * / % */
    @Override public void enterMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx) {
        String first, context = ctx.getText();
        Integer[] possibles = {context.lastIndexOf("*"), context.lastIndexOf("/"), context.lastIndexOf("%")};
        if (ctx.getText().lastIndexOf("*") == Collections.max(Arrays.asList(possibles))) {
            first = "*";
        } else if (ctx.getText().lastIndexOf("/") == Collections.max(Arrays.asList(possibles))) {
            first = "/";
        } else {
            first = "%";
        }
        if((ctx.getText().lastIndexOf(first) != -1)){
            if (!table.containsKey(first)){
                table.put(first, 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get(first);
                table.put(first, ++num_ocurrences);
                N1++;
            }            
        }
     }
     /**If statement */
     @Override public void enterIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
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
      }

     /*If-else statement */
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
    /* ciclo for */
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
     /* Para encontrar los identificadores  */
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
    /**Operacion and */
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

     /* operador %=  &= */
     @Override public void enterAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx) {
         
        Map<String, String> operatorMap = new HashMap<String, String>();
        {{
            operatorMap.put("=", null);
            operatorMap.put("*=", null);
            operatorMap.put("/=", null);
            operatorMap.put("%=", null);
            operatorMap.put("+=", null);
            operatorMap.put("-=", null);
            operatorMap.put("<<=", null);
            operatorMap.put(">>=", null);
            operatorMap.put(">>>=", null);
            operatorMap.put("&=", null);
            operatorMap.put("^=", null);
            operatorMap.put("|=", null);

        }};

        for (String x : operatorMap.keySet()) {
            if((ctx.getText().indexOf(x) != -1)){
                if (!table.containsKey(x)){
                    table.put(x, 1);
                    n1++;
                    N1++;
                }else{
                    int num_ocurrences = table.get(x);
                    table.put(x, ++num_ocurrences);
                    N1++;
                }
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
    /**Post increment a++ */
    @Override public void enterPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) {
        if((ctx.getText().indexOf("++") != -1)){
            if (!table.containsKey("++")){
                table.put("++", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("++");
                table.put("++",++num_ocurrences);
                N1++;
            }
        }
     }
     /**Preincremeto ++a */
    @Override public void enterPreIncrementExpression(Java8Parser.PreIncrementExpressionContext ctx) {
        if((ctx.getText().indexOf("++") != -1)){
            if (!table.containsKey("++")){
                table.put("++", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("++");
                table.put("++",++num_ocurrences);
                N1++;
            }
        }
    }
    /**Predecrement -- */
    @Override public void enterPreDecrementExpression(Java8Parser.PreDecrementExpressionContext ctx) {
        if((ctx.getText().indexOf("--") != -1)){
            if (!table.containsKey("--")){
                table.put("--", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("--");
                table.put("--",++num_ocurrences);
                N1++;
            }
        }
    }
    /**Relational expression <, >, >=, <= */
    @Override public void enterRelationalExpression(Java8Parser.RelationalExpressionContext ctx) {
        String first, context = ctx. getText();
        Integer[] possible = {context.lastIndexOf(">"), context.lastIndexOf("<"), context.lastIndexOf("<="), context.lastIndexOf(">=")};
        if (context.lastIndexOf("<=") == Collections.max(Arrays.asList(possible))) {
            first = "<=";            
        } else if (context.lastIndexOf(">=") == Collections.max(Arrays.asList(possible))) {
            first = ">=";
        } else if (context.lastIndexOf("<") == Collections.max(Arrays.asList(possible))) {
            first = "<";
        } else {
            first = ">";
        }
        if((ctx.getText().indexOf(first) != -1)){
            if (!table.containsKey(first)){
                table.put(first, 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get(first);
                table.put(first,++num_ocurrences);
                N1++;
            }
        }
    }
    /**EqualityExpression a==b, a!=b */
    @Override public void enterEqualityExpression(Java8Parser.EqualityExpressionContext ctx) {
        String first, context = ctx. getText();
        Integer[] possible = {context.lastIndexOf("=="), context.lastIndexOf("!=")};
        if (context.lastIndexOf("==") == Collections.max(Arrays.asList(possible))) {
            first = "==";            
        } else {
            first = "!=";
        }
        if((ctx.getText().indexOf(first) != -1)){
            if (!table.containsKey(first)){
                table.put(first, 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get(first);
                table.put(first,++num_ocurrences);
                N1++;
            }
        }
     }
     /* return statement*/
     @Override public void enterReturnStatement(Java8Parser.ReturnStatementContext ctx) {
        if((ctx.getText().indexOf("return") != -1)){
            if (!table.containsKey("return")){
                table.put("return", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("return");
                table.put("return",++num_ocurrences);
                N1++;
            }
        }
    }
    /* contador de declaraciones de [] arrays incluyendo matrices*/
    @Override public void enterDims(Java8Parser.DimsContext ctx) { 
        contadorArrays++;
        if(!table.containsKey("[]")){
            table.put("[]", 1);
            n1++;
            N1++;
        }else{
            int num_ocurrences = table.get("[]");
            table.put("[]", ++num_ocurrences);
            N1++;
        }
    }

    /* contador de imports*/
    @Override public void enterImportDeclaration(Java8Parser.ImportDeclarationContext ctx) { 
        contadorImports++;
        if(!table.containsKey("import")){
            table.put("import", 1);
            n1++;
            N1++;
        }else{
            int num_ocurrences = table.get("import");
            table.put("import", ++num_ocurrences);
            N1++;
        }
    }
    /* contador de clases*/
    @Override public void enterNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) { 
        contadorClases++;
        if(!table.containsKey("class")){
            table.put("class", 1);
            n1++;
            N1++;
        }else{
            int num_ocurrences = table.get("class");
            table.put("class", ++num_ocurrences);
            N1++;
        }
    }
    /* contador de los new */
    @Override public void enterArrayCreationExpression(Java8Parser.ArrayCreationExpressionContext ctx) { 
        if(!table.containsKey("new")){
            table.put("new", 1);
            n1++;
            N1++;
        }else{
            int num_ocurrences = table.get("new");
            table.put("new", ++num_ocurrences);
            N1++;
        }
    }

    /*contador para los modificadores de acceso de las clases (private protected public) */
    @Override public void enterClassModifier(Java8Parser.ClassModifierContext ctx) { 
        if(!table.containsKey("classMod")){
            table.put("classMod", 1);
            n1++;
            N1++;
        }else{
            int num_ocurrences = table.get("classMod");
            table.put("classMod", ++num_ocurrences);
            N1++;
        }
    }

    /* contador modificadores para funciones (private public static ...) */
    @Override public void enterMethodModifier(Java8Parser.MethodModifierContext ctx) { 
        if(!table.containsKey("methodMod")){
            table.put("methodMod", 1);
            n1++;
            N1++;
        }else{
            int num_ocurrences = table.get("methodMod");
            table.put("methodMod", ++num_ocurrences);
            N1++;
        }
    }

    /* contador para cuando se usa lenght */
    @Override public void enterExpressionName(Java8Parser.ExpressionNameContext ctx) { 
        if((ctx.getText().indexOf("length") != -1)){
            if (!table.containsKey("length")){
                table.put("length", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("length");
                table.put("length",++num_ocurrences);
                N1++;
            }
        }
    }

    /* contador para la expresion this */
    @Override public void enterPrimary(Java8Parser.PrimaryContext ctx) { 
        if((ctx.getText().indexOf("this") != -1)){
            if (!table.containsKey("this")){
                table.put("this", 1);
                n1++;
                N1++;
            }else{
                int num_ocurrences = table.get("this");
                table.put("this",++num_ocurrences);
                N1++;
            }
        }
    }
    /* contador de todos los tipos de dato (int float boolean ...) */
    @Override public void enterUnannType(Java8Parser.UnannTypeContext ctx) { 
        contadorTiposDato++;
        String first, context = ctx.getText();
        String[] names = { "int", "float", "short", "byte", "long", "double", "char", "boolean", "String"};
        Integer[] possibles = {
            context.indexOf("int"),
            context.indexOf("float"),
            context.indexOf("short"),
            context.indexOf("byte"),
            context.indexOf("long"),
            context.indexOf("double"),
            context.indexOf("char"),
            context.indexOf("boolean"),
            context.indexOf("String"),
        };
        int max = Collections.max(Arrays.asList(possibles));
        int index = Arrays.asList(possibles).indexOf(max);
        first = names[index];

        if((ctx.getText().indexOf(first) != -1)){
            if (!datatypes.containsKey(first)){
                datatypes.put(first, 1);
                n2++;
                N2++;
            }else{
                int num_ocurrences = datatypes.get(first);
                datatypes.put(first,++num_ocurrences);
                N2++;
            }
        }        
    }
    /* contador de veces que se llama un id como operando*/
    @Override public void enterVariableDeclaratorId(Java8Parser.VariableDeclaratorIdContext ctx) {
        contadorIds++;
        N2++;
     }
     /* contador de tipos de dato para funciones */
     @Override public void enterResult(Java8Parser.ResultContext ctx) {
         
        String first, context = ctx.getText();
        String[] names = { "int", "float", "short", "byte", "long", "double", "char", "boolean", "String", "void", "signed", "unsigned"};
        Integer[] possibles = {
            context.indexOf("int"),
            context.indexOf("float"),
            context.indexOf("short"),
            context.indexOf("byte"),
            context.indexOf("long"),
            context.indexOf("double"),
            context.indexOf("char"),
            context.indexOf("boolean"),
            context.indexOf("String"),
            context.indexOf("void"),
            context.indexOf("signed"),
            context.indexOf("unsigned"),
        };
        int max = Collections.max(Arrays.asList(possibles));
        int index = Arrays.asList(possibles).indexOf(max);
        first = names[index];

        if((ctx.getText().indexOf(first) != -1)){
            if (!methodtypes.containsKey(first)){
                methodtypes.put(first, 1);
                n2++;
                N2++;
            }else{
                int num_ocurrences = methodtypes.get(first);
                methodtypes.put(first,++num_ocurrences);
                N2++;
            }
        }        
     }


}
