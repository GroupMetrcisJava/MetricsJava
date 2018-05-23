// import de librerias de runtime de ANTLR
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.File;
import org.antlr.v4.gui.TreeViewer;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class correr {
  public static void main(String[] args) throws Exception {
    try{
	// crear un analizador léxico que se alimenta a partir de la entrada (archivo  o consola)
	Java8Lexer lexer;
	if (args.length>0)
	  lexer = new Java8Lexer(new ANTLRFileStream(args[0]));
	else
	  lexer = new Java8Lexer(new ANTLRInputStream(System.in));
	// Identificar al analizador léxico como fuente de tokens para el sintactico
	CommonTokenStream tokens = new CommonTokenStream(lexer);
	// Crear el analizador sintáctico que se alimenta a partir del buffer de tokens
	Java8Parser parser = new Java8Parser(tokens);
  ParseTree tree = parser.compilationUnit(); // comienza el análisis en la regla inicial

  //walkers***********************************************
  ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new metricador(), tree);
        System.out.println();
  //*************************************************** */

    //System.out.println(tree.toStringTree(parser)); // imprime el árbol en forma textual
    //show AST in GUI
    JFrame frame = new JFrame("Antlr AST");
    JPanel panel = new JPanel();
    TreeViewer viewr = new TreeViewer(Arrays.asList(
            parser.getRuleNames()),tree);
    viewr.setScale(1.5);//scale a little
    panel.add(viewr);
    frame.add(panel);
    //scroll
    JScrollPane scrollPane = new JScrollPane(panel);
    frame.getContentPane().add(scrollPane);
    frame.pack();
    //
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500,400);
    frame.setVisible(true);
    } catch (Exception e){
	System.err.println("Error (Test): " + e);
    }
  }
}