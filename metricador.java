import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
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
    int size = 0;
    Map <String, Integer> table = new HashMap<String, Integer>();
    Map <String, Integer> datatypes = new HashMap<String, Integer>();
    Map <String, Integer> methodtypes = new HashMap<String, Integer>();

    Map <String, Integer> tableIndex = new HashMap<String, Integer>();
    Map <Integer, String> tableName = new HashMap<Integer, String>();
    List< Map <String, Float> > tables = new ArrayList<Map<String, Float>>();

    /*para sacar los metodos grafo*/
    String nameClass = "";
    String nameMethod = "";
    String nameVariable = "";
    String nameWhile = "";
    String nameFor = "";
    String nameIf = "";
    ArrayList<String []> graph= new ArrayList<String []>();

    String currentTable;

    public void countOperators(String ctx, String first) {
        if (ctx.indexOf(first) != -1) {            
            if (!table.containsKey(first)) {                
                table.put(first, 1);
                n1++;
                N1++;                
            } else {                
                int num_ocurrences = table.get(first);
                table.put(first,++num_ocurrences);
                N1++;                
            }
        }
    }
    public void countOperands(String ctx, String first) {
        if (ctx.indexOf(first) != -1) {            
            if (!datatypes.containsKey(first)) {                
                datatypes.put(first, 1);
                n2++;
                N2++;                
            } else {                
                int num_ocurrences = datatypes.get(first);
                datatypes.put(first,++num_ocurrences);
                N2++;                
            }
        }
    }

    @Override public void enterLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx) { 
        contadorLocales++;
    }

    @Override public void enterMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
        String context = ctx.getText();
        System.out.println("Method Context: " + context);
        String name = context.substring(0, context.indexOf('('));
        Map<String, Float> newTable = new HashMap<String, Float>();
        {{
            newTable.put("n1", (float)0);
            newTable.put("n2", (float)0);
            newTable.put("N1", (float)0);
            newTable.put("N2", (float)0);
        }}        
        tableIndex.put(name, tables.size());
        tableName.put(tables.size(), name);
        tables.add(newTable);
        currentTable = name;

        nameMethod = ctx.Identifier().getText();
        String arrayCadena [] = new String [2];
        arrayCadena[0] = nameClass;
        arrayCadena[1] = nameMethod;
        graph.add(arrayCadena); 
     }

    @Override public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        contadorFunciones++;
        Map<String,Float> auxTable = tables.get(tableIndex.get(currentTable));
        System.out.println("# Variables locales: " + contadorLocales);
        contadorLocales = 0;
        System.out.println("\nNombre tabla: " + currentTable);   
        System.out.println("tabla: "+ auxTable);
        System.out.println("tipos dato: "+datatypes);
        System.out.println("HALSTEAD MEDIDAS: ------");
        /*
        System.out.println("N1: " + auxTable.get("N1"));
        System.out.println("N2: " + auxTable.get("N2"));
        System.out.println("n1: " + auxTable.get("n1"));
        System.out.println("n2: " + auxTable.get("n2"));
        */
        System.out.println("N1: " + N1);
        System.out.println("N2: " + N2);
        System.out.println("n1: " + n1);
        System.out.println("n2: " + n2);
        double N = N1 + N2;
        double V = N * (Math.log(n1+n2) / (Math.log(2)));  
        double L = (2.0*n2) / (n1*N2);
        double E = (n1+N2 * (N1+N2)* (Math.log(n1+n2) / (Math.log(2))) ) / (2*n2);    
        System.out.println("Longitud del programa: "+ (int) N);
        System.out.println("Volumen del programa: "+V);
        System.out.println("Nivel de especificacion de abtraccion: "+L);
        System.out.println("Esfuerzo del programa: "+E);
        System.out.println();

        tables.remove(tables.size() - 1);
        if (tables.size() > 0) {
            currentTable = tableName.get(tables.size());
        } else {
            currentTable = "";
        }
        nameMethod = "";
        
     }
     @Override public void exitCompilationUnit(Java8Parser.CompilationUnitContext ctx) { 
         System.out.println("# Funciones: " + contadorFunciones);
         System.out.println("# Imports:  "+ contadorImports);
         System.out.println("# Clases: "+ contadorClases);
         System.out.println("# Arreglos: "+ contadorArrays);

         System.out.println(tables);
         System.out.println(tableIndex);
         System.out.println("DEPENDENCIAS:");
         ListIterator<String []> itr = graph.listIterator();
         while(itr.hasNext()){
             String [] palabras = itr.next();
             String edges = "";
             for(int i = 0; i < palabras.length; i++){
                 edges = edges + "|" + palabras[i];
             }
             System.out.println(edges);
         }
         
     }
    
     /* Operacionaes de tipo A -> a = b */
     @Override public void enterVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
        //System.out.println("contexto declarator: " + ctx.getText());             
         countOperators(ctx.getText(), "=");
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
        countOperators(context, first); 
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
        countOperators(context, first);
     }
     /**If statement */
     @Override public void enterIfThenStatement(Java8Parser.IfThenStatementContext ctx) {        
        countOperators(ctx.getText(), "if");
        nameIf = "IF";
        String arrayCadena [] = new String [2];
        if(nameMethod.equals("")){
            arrayCadena[0] = nameClass;
        }else{
            arrayCadena[0] = nameMethod;
        }
        arrayCadena[1] = nameIf;
        graph.add(arrayCadena);
      }

      @Override public void exitIfThenStatement(Java8Parser.IfThenStatementContext ctx) { 
          nameIf = "";
      }

     /*If-else statement */
     @Override public void enterIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
        countOperators(ctx.getText(), "if");
        countOperators(ctx.getText(), "else");
      }
      /**Switch (solo switch, sin instrucciones) */
      @Override public void enterSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
        countOperators(ctx.getText(), "switch");
    } 
    /**Encuentra case y default*/
    @Override public void enterSwitchLabel(Java8Parser.SwitchLabelContext ctx) {
        countOperators(ctx.getText(), "case");
        countOperators(ctx.getText(), "default");
    }
    /**Encuentra break */
    @Override public void enterBreakStatement(Java8Parser.BreakStatementContext ctx) {
        countOperators(ctx.getText(), "break");
    }
    /* ciclo for */
    @Override public void enterForStatement(Java8Parser.ForStatementContext ctx) {
        countOperators(ctx.getText(), "for");
        nameFor = "FOR";
        String arrayCadena [] = new String [2];
        if(nameMethod.equals("")){
            arrayCadena[0] = nameClass;
        }else{
            arrayCadena[0] = nameMethod;
        }
        arrayCadena[1] = nameFor;
        graph.add(arrayCadena);
    }

    @Override public void exitForStatement(Java8Parser.ForStatementContext ctx) { 
        nameFor = "";
    }
    /**Continue statement */
    @Override public void enterContinueStatement(Java8Parser.ContinueStatementContext ctx) {
        countOperators(ctx.getText(), "continue");
    }
    /**Statement do-while */
    @Override public void enterDoStatement(Java8Parser.DoStatementContext ctx) {
        countOperators(ctx.getText(), "do");
        countOperators(ctx.getText(), "while");
    }
    /**While statement */
    @Override public void enterWhileStatement(Java8Parser.WhileStatementContext ctx) {
        countOperators(ctx.getText(), "while");
        nameWhile = "WHILE";
        String arrayCadena [] = new String [2];
        if(nameMethod.equals("")){
            arrayCadena[0] = nameClass;
        }else{
            arrayCadena[0] = nameMethod;
        }
        arrayCadena[1] = nameWhile;
        graph.add(arrayCadena);
    }

    @Override public void exitWhileStatement(Java8Parser.WhileStatementContext ctx) { 
        nameWhile = "";
    }
    /**TRue and False values in boolean type variables */
     /* Para encontrar los identificadores  */
    @Override public void enterLiteral(Java8Parser.LiteralContext ctx) {
        countOperators(ctx.getText(), "true");
        countOperators(ctx.getText(), "false");
    }
    /**Operacion and */
    @Override public void enterConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx) { 
        countOperators(ctx.getText(), "&&");
     }

     /*operacion or*/
     @Override public void enterConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx) { 
        countOperators(ctx.getText(), "||");
     }

    /* operacion not ! */
     @Override public void enterUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx) { 
        countOperators(ctx.getText(), "!");
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
            countOperators(ctx.getText(), x);
        }       
     }

     /* operador bitwise & */
     @Override public void enterAndExpression(Java8Parser.AndExpressionContext ctx) { 
        countOperators(ctx.getText(), "&");
    }
    /**Post increment a++ */
    @Override public void enterPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) {
        countOperators(ctx.getText(), "++");
     }
     /**Preincremeto ++a */
    @Override public void enterPreIncrementExpression(Java8Parser.PreIncrementExpressionContext ctx) {
        countOperators(ctx.getText(), "++");
    }
    /**Predecrement -- */
    @Override public void enterPreDecrementExpression(Java8Parser.PreDecrementExpressionContext ctx) {
        countOperators(ctx.getText(), "--");
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
        countOperators(ctx.getText(), first);
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
        countOperators(ctx.getText(), first);
     }
     /* return statement*/
     @Override public void enterReturnStatement(Java8Parser.ReturnStatementContext ctx) {
        countOperators(ctx.getText(), "return");
    }
    /* contador de declaraciones de [] arrays incluyendo matrices*/
    @Override public void enterDims(Java8Parser.DimsContext ctx) { 
        contadorArrays++;
        countOperators(ctx.getText(), "[]");
    }

    /* contador de imports*/
    @Override public void enterImportDeclaration(Java8Parser.ImportDeclarationContext ctx) { 
        contadorImports++;
        countOperators(ctx.getText(), "import");
    }
    /* contador de clases*/
    @Override public void enterNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) { 
        contadorClases++;
        countOperators(ctx.getText(), "class");
        /* para incluir clases en los grafos*/
        if(nameClass.equals("")){
            nameClass = ctx.Identifier().getText();
        }else{
            String subClass = ctx.Identifier().getText();
            String arrayCadena [] = new String [2];
            arrayCadena[0] = nameClass;
            arrayCadena[1] = subClass;
            graph.add(arrayCadena);
        }
        
        
    }

    /*reset del nombre de clase en analisis*/
    @Override public void exitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) { 
        nameClass = "";
    }

    /* contador de los new */
    @Override public void enterArrayCreationExpression(Java8Parser.ArrayCreationExpressionContext ctx) { 
        countOperators(ctx.getText(), "new");
    }

    /*contador para los modificadores de acceso de las clases (private protected public) */
    @Override public void enterClassModifier(Java8Parser.ClassModifierContext ctx) { 
        countOperators(ctx.getText(), "classMod");
    }

    /* contador modificadores para funciones (private public static ...) */
    @Override public void enterMethodModifier(Java8Parser.MethodModifierContext ctx) { 
        countOperators(ctx.getText(), "methodMod");
    }

    /* contador para cuando se usa lenght */
    @Override public void enterExpressionName(Java8Parser.ExpressionNameContext ctx) { 
        countOperators(ctx.getText(), "length");
    }

    /* contador para la expresion this */
    @Override public void enterPrimary(Java8Parser.PrimaryContext ctx) { 
        countOperators(ctx.getText(), "methodMod");
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
        countOperands(ctx.getText(), first); 
    }
    /* contador de veces que se llama un id como operando*/
    @Override public void enterVariableDeclaratorId(Java8Parser.VariableDeclaratorIdContext ctx) {
        String first = ctx.getText();        
        contadorIds++;
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
        nameVariable = ctx.Identifier().getText();
        String arrayCadena [] = new String [2];
        if(nameMethod.equals("")){
            arrayCadena[0] = nameClass;
        }else{
            arrayCadena[0] = nameMethod;
        }
        arrayCadena[1] = nameVariable;
        graph.add(arrayCadena); 
    }

    @Override public void exitVariableDeclaratorId(Java8Parser.VariableDeclaratorIdContext ctx) { 
        nameVariable = "";
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
        countOperands(ctx.getText(), first); 
     }


}
