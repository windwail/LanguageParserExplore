package ru.neirojet;

import org.junit.Assert;
import org.junit.Test;
import ru.neirojet.ast.NJNode;
import ru.neirojet.ast.TokenType;
import static org.junit.Assert.*;

/**
 * Created by icetusk on 15.01.17.
 */
public class ParsingTest {
    @Test
    public void testParsing() throws Exception {
        NJNode n;

        //n.printNodes();
        //if(true) return;

        String btn = "button1.text = '54';";
        n = new NJNode(btn);
        n.splitTokensByLevel();
        n.calculateValue();

        String abd = "one.two.three(5+5,subprocess.getx(3))+2";
        n = new NJNode(abd);
        n.splitTokensByLevel();
        assertTrue(n.getTokens().size() == 1); // mutated
        assertTrue (n.getType() == TokenType.BINARY);
        assertTrue (n.getChildren().size() == 2);
        assertTrue (n.getChildren().get(0).getTokens().size() == 5);
        assertTrue (n.getChildren().get(0).getTokens().get(0).type == TokenType.SYMBOLIC);

        String var = "bool x;";
        n = new NJNode(var);
        n.splitTokensByLevel();
        assertTrue (n.getTokens().size() == 1); // mutated
        assertTrue (n.getTokens().get(0).type == TokenType.TYPE);
        assertTrue (n.getChildren().size() == 1);
        assertTrue (n.getChildren().get(0).getTokens().size() == 1);
        assertTrue (n.getChildren().get(0).getTokens().get(0).type == TokenType.VARNAME);

        String simple_func = "f((1+2),(3*(2+1)))";
        n = new NJNode(simple_func);
        n.splitTokensByLevel();
        assertTrue (n.getTokens().size() == 1); // mutated
        assertTrue (n.getTokens().get(0).type == TokenType.CALL);
        assertTrue (n.getChildren().size() == 2);
        assertTrue (n.getChildren().get(0).getTokens().size() == 1);
        assertTrue (n.getChildren().get(0).getTokens().get(0).type == TokenType.BINARY);
        assertTrue (n.getChildren().get(0).getTokens().get(0).text.equals("+"));
        assertTrue (n.getChildren().get(1).getTokens().get(0).type == TokenType.BINARY);
        assertTrue (n.getChildren().get(1).getTokens().get(0).text.equals("*"));
        assertTrue (n.getChildren().get(1).
                getChildren().get(0).getTokens().get(0).text.equals("3"));
        assertTrue (n.getChildren().get(1).getChildren().get(1).getChildren().size() == 2);

        String o2 = "x+b||c*d";
        n = new NJNode(o2);
        n.collectTokens();
        assertTrue (n.detectOpLevel() == 3);

        String o3 = "x+b==c*d";
        n = new NJNode(o3);
        n.collectTokens();
        assertTrue (n.detectOpLevel() == 8);

        String func = "some(1,2,3)";
        n = new NJNode(func);
        assertTrue (n.nextToken().text.equals("some"));
        assertTrue (n.nextToken().text.equals("1,2,3"));

        String ops2 = "x++ + ++y-z*3";
        n = new NJNode(ops2);
        assertTrue (n.nextToken().text.equals("x"));
        assertTrue (n.nextToken().text.equals("++"));
        assertTrue (n.nextToken().text.equals("+"));
        assertTrue (n.nextToken().text.equals("++"));
        assertTrue (n.nextToken().text.equals("y"));
        assertTrue (n.nextToken().text.equals("-"));
        assertTrue (n.nextToken().text.equals("z"));
        assertTrue (n.nextToken().text.equals("*"));
        assertTrue (n.nextToken().text.equals("3"));
        assertTrue (n.nextToken().type == TokenType.EOL);

        String ops = "a==b<=c>=d";
        n = new NJNode(ops);
        assertTrue (n.nextToken().text.equals("a"));
        assertTrue (n.nextToken().text.equals("=="));
        assertTrue (n.nextToken().text.equals("b"));
        assertTrue (n.nextToken().text.equals("<="));
        assertTrue (n.nextToken().text.equals("c"));
        assertTrue (n.nextToken().text.equals(">="));
        assertTrue (n.nextToken().text.equals("d"));
        assertTrue (n.nextToken().type == TokenType.EOL);

        String brackets = "(((1)(2))((3)(4)))";
        n = new NJNode(brackets);
        assertTrue (n.nextToken().text.equals("((1)(2))((3)(4))"));
        assertTrue (n.nextToken().type == TokenType.EOL);


        String code = "x=(\"comon\")";
        n = new NJNode(code);
        assertTrue (n.nextToken().text.equals("x"));
        assertTrue (n.nextToken().text.equals("="));
        assertTrue (n.nextToken().text.equals("\"comon\""));
        assertTrue (n.nextToken().type == TokenType.EOL);

        String code2 = "int x=person.address.index+(3*4*3+1)";
        n = new NJNode(code2);
        assertTrue (n.nextToken().text.equals("int"));
        assertTrue (n.nextToken().text.equals("x"));
        assertTrue (n.nextToken().text.equals("="));
        assertTrue (n.nextToken().text.equals("person"));
        assertTrue (n.nextToken().text.equals("."));
        assertTrue (n.nextToken().text.equals("address"));
        assertTrue (n.nextToken().text.equals("."));
        assertTrue (n.nextToken().text.equals("index"));
        assertTrue (n.nextToken().text.equals("+"));
        assertTrue (n.nextToken().text.equals("3*4*3+1"));
        assertTrue (n.nextToken().type == TokenType.EOL);

        String code3 = "(o),(o),()";
        n = new NJNode(code3);
        assertTrue (n.nextToken().text.equals("o"));
        assertTrue (n.nextToken().text.equals(","));
        assertTrue (n.nextToken().text.equals("o"));
        assertTrue (n.nextToken().text.equals(","));
        assertTrue (n.nextToken().text.equals(""));
        assertTrue (n.nextToken().type == TokenType.EOL);
    }
}
