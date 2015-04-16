package dk.aau.sw402F15;

import dk.aau.sw402F15.ScopeChecker.ScopeChecker;
import dk.aau.sw402F15.TypeChecker.Symboltable.Scope;
import dk.aau.sw402F15.TypeChecker.TypeChecker;
import dk.aau.sw402F15.parser.lexer.Lexer;
import dk.aau.sw402F15.parser.lexer.LexerException;
import dk.aau.sw402F15.parser.node.Start;
import dk.aau.sw402F15.parser.parser.Parser;
import dk.aau.sw402F15.parser.parser.ParserException;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String code =           "bool aaa = 5 + 3 > 7;\n" +
                                "a = 10; int i(){}\n" +
                                "int a = 1 + 5 - 6 * 7;\n" +
                                "bool a = true && false;\n" +
                                "bool b = false || true;\n" +
                                "bool c = false;\n" +
                                "bool d = false == false;\n" +
                                "bool e = true == false;\n" +
                                "bool f = true != false;\n" +
                                "bool g = true != true;\n" +
                                "if (true) { bool b = true; }\n" +
                                "if (false) { bool b = false; b(); bool a = I#0.0; } else { bool c; bool d = d; }\n" +
                                "if (false) { bool c = false; int i = i(); } else if (false) { bool c = true; } else {}\n" +
                                "void b() { bool b = false; b(); return; }\n" +
                                "bool b(int c, int j) { bool b = false; port p = AQ#0.1; return true; }\n" +
                                "b++; ++i; --i; ++i;\n" +
                                "a+=3; b-=2; c*=4; d/=1;\n" +
                                "b(); void b() { } void c() { } port a = I#i; port p = Q#I#1;\n" +
                                "int b = z++; int c = ++z; z += 3; int z = 3;\n" +
                                "if (i > 0) { } else if (i < 0) { } else { }\n" +
                                "int j = i(10);\n" +
                                "for (int i = 0; i < 10; i++) {} for (i = 0; i > 0; i+=3) {}\n" +
                                "bool b = !true; bool b = !(true) && !true;\n" +
                                "struct a { } struct b { int i = 0; int j = 0; int c = 0; }\n" +
                                "a.a.a.a = a.a.a.a;\n" +
                                "int i = a.aa; a = a; a a = b;\n" +
                                "int i = a().a.a().a;\n" +
                                "a().a().b().a.a.b();\n" +
                                "struct b { void b() { return; } int a() { return 1; } } ";

        code = "int func(){const int i = 7; i = 2; return 5;}";

       /* code = "const int i = 1;\n" +
                "int func(int p){\n" +
                    "i = func(9);\n" +
                    "float f = 7.5;\n" +
                    "return 6;\n" +
                "}\n";*/

                //"void call(){\n" +
                    //"func(true); }";

        System.out.println(code);

        try {
            Reader reader;
            if(args.length != 0)
                reader = new FileReader(args[0]);
            else
                reader = new StringReader(code);
            Parser parser = new Parser(new Lexer(new PushbackReader(reader, 1024)));
            Start tree = parser.parse();

            // Print tree
            tree.apply(new PrettyPrinter());

            ScopeChecker checker = new ScopeChecker();
            tree.apply(checker);

            tree.apply(new TypeChecker(checker.getSymbolTable()));
            //tree.apply(new ExpressionEvaluator());

        } catch (ParserException e) {
            e.printStackTrace();
        } catch (LexerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}