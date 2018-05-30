import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.*;

public class metricador extends Java8BaseListener{
    static int contadorFunciones = 0;
    static int contadorLocales = 0;
    static int contadorImports = 0;
    static int contadorClases = 0;
    static int contadorArrays = 0;
    static int contadorTiposDato = 0;
    static int contadorIds = 0;
    
    
    /**Mapa que guarda los valores numericos por funcion */
    Map <String, Map<String, Float>> numFunc = new HashMap<String, Map<String, Float>>();
    /**Mapa que guarda la tabla de simbolos por funcion */
    Map <String, Map<String, Integer>> symFunc = new HashMap<String, Map<String, Integer>>();
    /**Mapa que guarda la tabla de operandos por funcion */
    Map <String, Map<String, Integer>> varFunc = new HashMap<String, Map<String, Integer>>();

    /**Mapa que guarda los valores numericos por clase */
    Map <String, Map<String, Float>> numClass = new HashMap<String, Map<String, Float>>();
    /**Mapa que guarda la tabla de operandos por funcion */
    Map <String, Map<String, Integer>> varClass = new HashMap<String, Map<String, Integer>>();
    /**Mapa que guarda la tabla de operandos por funcion */
    Map <String, Map<String, Integer>> symClass = new HashMap<String, Map<String, Integer>>();

    /**Lista para ver orden de las clases */
    List <String> classes = new ArrayList<String>();
    /**Nombre de la tabla actual */
    String currentTable;

    public void countOperators(String ctx, String first) {
        if (ctx.indexOf(first) != -1) {
            /* System.out.println("operators");            
            System.out.println("context: "+ ctx);
            System.out.println("first: "+ first); */
            Map<String, Float> numAux = new HashMap<String, Float>();
            Map<String, Integer> symAux = new HashMap<String, Integer>();
            if (currentTable == null && classes.size() > 0) {
                //System.out.println("Has classes");
                numAux = numClass.get(classes.get(classes.size() - 1));
                symAux = symClass.get(classes.get(classes.size() - 1));
            } else if (currentTable != null) {
                //System.out.println("No classes");
                numAux = numFunc.get(currentTable);
                symAux = symFunc.get(currentTable);
            }                
            float n1 = numAux.get("n1"), N1 = numAux.get("N1");            
            if (!symAux.containsKey(first)) {                
                symAux.put(first, 1);                
                numAux.put("n1", ++n1);
            } else {                
                int num_ocurrences = symAux.get(first);
                symAux.put(first,++num_ocurrences);                
            }            
            numAux.put("N1", ++N1);
            if (currentTable == null && classes.size() > 0) {
                symClass.put(classes.get(classes.size() - 1), symAux);
                numClass.put(classes.get(classes.size() - 1), numAux);
                /* System.out.println("symClass: " + symClass);            
                System.out.println("numClass: " + numClass); */
            } else if (currentTable != null) {
                symFunc.put(currentTable, symAux);
                numFunc.put(currentTable, numAux);
                /* System.out.println("symFunc: " + symFunc);            
                System.out.println("numFunc: " + numFunc); */
            }            
            
        }
    }
    public void countOperands(String ctx, String first) {
        
        if (ctx.indexOf(first) != -1) {
            /* System.out.println("operands");
            System.out.println("context: "+ ctx);
            System.out.println("first: "+ first); */
            Map<String, Float> numAux = new HashMap<String, Float>();
            Map<String, Integer> varAux = new HashMap<String, Integer>();
            
            if (currentTable == null && classes.size() > 0) {
                //System.out.println("Has classes");
                numAux = numClass.get(classes.get(classes.size() - 1));
                varAux = varClass.get(classes.get(classes.size() - 1));
            } else if (currentTable != null) {
                //System.out.println("No classes");
                numAux = numFunc.get(currentTable);
                varAux = varFunc.get(currentTable);
            }                
            float n2 = numAux.get("n2"), N2 = numAux.get("N2");            
            if (!varAux.containsKey(first)) {                
                varAux.put(first, 1);                
                numAux.put("n2", ++n2);                
            } else {                
                int num_ocurrences = varAux.get(first);
                varAux.put(first,++num_ocurrences);                
            }                    
            numAux.put("N2", ++N2);
            if (currentTable == null && classes.size() > 0) {
                varClass.put(classes.get(classes.size() - 1), varAux);
                numClass.put(classes.get(classes.size() - 1), numAux);
                /* System.out.println("varClass: " + varClass);            
                System.out.println("numClass: " + numClass); */
            } else if (currentTable != null) {
                varFunc.put(currentTable, varAux);
                numFunc.put(currentTable, numAux);
                /* System.out.println("varFunc: " + varFunc);            
                System.out.println("numFunc: " + numFunc); */
            }            
        }
    }

    @Override public void enterLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx) { 
        contadorLocales++;
    }

    @Override public void enterMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
        String context = ctx.getText();
        //System.out.println("Method Context: " + context);
        String name = context.substring(0, context.indexOf('('));
        Map<String, Float> newTable = new HashMap<String, Float>();
        Map<String, Integer> newVarTable = new HashMap<String, Integer>();
        {{
            newTable.put("n1", (float)0);
            newTable.put("n2", (float)0);
            newTable.put("N1", (float)0);
            newTable.put("N2", (float)0);
        }}        
        numFunc.put(name, newTable);
        varFunc.put(name, newVarTable);        
        symFunc.put(name, newVarTable);
        currentTable = name;
        /* System.out.println("Añadida nueva tabla :" + currentTable);
        System.out.println("numFunc: " + numFunc);          
        System.out.println("varFunc: " + varFunc);           */
     }

    @Override public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        contadorFunciones++;
        Map<String,Float> numAux = numFunc.get(currentTable);
        System.out.println("# Variables locales: " + contadorLocales);
        contadorLocales = 0;
        System.out.println("\nNombre tabla: " + currentTable);   
        System.out.println("tabla: "+ numAux);
        //System.out.println("tipos dato: "+datatypes);
        System.out.println("HALSTEAD MEDIDAS: ------");
        
        float n1 =  numAux.get("n1"), N1 =  numAux.get("N1"), n2 =  numAux.get("n2"), N2 =  numAux.get("N2");
        System.out.println("N1: " + (int) N1);
        System.out.println("N2: " + (int) N2);
        System.out.println("n1: " + (int) n1);
        System.out.println("n2: " + (int) n2);
        double N = N1 + N2;
        double V = N * (Math.log(n1+n2) / (Math.log(2)));  
        double L = (2.0*n2) / (n1*N2);
        double E = (n1+N2 * (N1+N2)* (Math.log(n1+n2) / (Math.log(2))) ) / (2*n2);    
        System.out.println("Longitud del programa: "+ (int) N);
        System.out.println("Volumen del programa: "+V);
        System.out.println("Nivel de especificacion de abtraccion: "+L);
        System.out.println("Esfuerzo del programa: "+E);
        System.out.println();
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
        //countOperators(ctx.getText(), "import");
    }
    /* contador de clases*/
    @Override public void enterNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) { 
        contadorClases++;
        String context = ctx.getText();
        String name = context.substring(context.indexOf("class"), context.indexOf('{'));                
        Map<String, Float> newTable = new HashMap<String, Float>();
        Map<String, Integer> newVarTable = new HashMap<String, Integer>();
        {{
            newTable.put("n1", (float)0);
            newTable.put("n2", (float)0);
            newTable.put("N1", (float)0);
            newTable.put("N2", (float)0);
        }}        
        numClass.put(name, newTable);
        varClass.put(name, newVarTable);
        symClass.put(name, newVarTable);
        classes.add(name);
        /* System.out.println(numClass);
        System.out.println(varClass);
        System.out.println(classes);  */      
    }
    /* contador de los new */
    @Override public void enterArrayCreationExpression(Java8Parser.ArrayCreationExpressionContext ctx) { 
        countOperators(ctx.getText(), "new");
    }

    /*contador para los modificadores de acceso de las clases (private protected public) */
    @Override public void enterClassModifier(Java8Parser.ClassModifierContext ctx) { 
        //countOperators(ctx.getText(), "classMod");
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
        countOperands(first, first);
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
