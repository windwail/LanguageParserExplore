/**
 * Created by icetsuk on 09.01.17.
 */
public class Main {

    public static void test() {
        NJNode n;

        String o1 = "5+(5+3)*7";
        String abd = "some.some.one(abc)+";
        abd = "x=fact.some(1,2,3)+10";
        n = new NJNode(abd);
        //n.collectTokens();
        //assert (n.detectOpLevel() == 1);
        n.splitTokensByLevel();
        //NJNode.printNodes(n,1);

        String o2 = "x+b||c*d";
        n = new NJNode(o2);
        n.collectTokens();
        assert (n.detectOpLevel() == 3);

        String o3 = "x+b==c*d";
        n = new NJNode(o3);
        n.collectTokens();
        assert (n.detectOpLevel() == 8);

        String func = "some(1,2,3)";
        n = new NJNode(func);
        assert (n.nextToken().text.equals("some"));
        assert (n.nextToken().text.equals("1,2,3"));

        String ops2 = "x++ + ++y-z*3";
        n = new NJNode(ops2);
        assert (n.nextToken().text.equals("x"));
        assert (n.nextToken().text.equals("++"));
        assert (n.nextToken().text.equals("+"));
        assert (n.nextToken().text.equals("++"));
        assert (n.nextToken().text.equals("y"));
        assert (n.nextToken().text.equals("-"));
        assert (n.nextToken().text.equals("z"));
        assert (n.nextToken().text.equals("*"));
        assert (n.nextToken().text.equals("3"));
        assert (n.nextToken().type == TokenType.EOL);

        String ops = "a==b<=c>=d";
        n = new NJNode(ops);
        assert (n.nextToken().text.equals("a"));
        assert (n.nextToken().text.equals("=="));
        assert (n.nextToken().text.equals("b"));
        assert (n.nextToken().text.equals("<="));
        assert (n.nextToken().text.equals("c"));
        assert (n.nextToken().text.equals(">="));
        assert (n.nextToken().text.equals("d"));
        assert (n.nextToken().type == TokenType.EOL);

        String brackets = "(((1)(2))((3)(4)))";
        n = new NJNode(brackets);
        assert (n.nextToken().text.equals("((1)(2))((3)(4))"));
        assert (n.nextToken().type == TokenType.EOL);


        String code = "x=(\"comon\")";
        n = new NJNode(code);
        assert (n.nextToken().text.equals("x"));
        assert (n.nextToken().text.equals("="));
        assert (n.nextToken().text.equals("\"comon\""));
        assert (n.nextToken().type == TokenType.EOL);

        String code2 = "int x=person.address.index+(3*4*3+1)";
        n = new NJNode(code2);
        assert (n.nextToken().text.equals("int"));
        assert (n.nextToken().text.equals("x"));
        assert (n.nextToken().text.equals("="));
        assert (n.nextToken().text.equals("person"));
        assert (n.nextToken().text.equals("."));
        assert (n.nextToken().text.equals("address"));
        assert (n.nextToken().text.equals("."));
        assert (n.nextToken().text.equals("index"));
        assert (n.nextToken().text.equals("+"));
        assert (n.nextToken().text.equals("3*4*3+1"));
        assert (n.nextToken().type == TokenType.EOL);

        String code3 = "(o),(o),()";
        n = new NJNode(code3);
        assert (n.nextToken().text.equals("o"));
        assert (n.nextToken().text.equals(","));
        assert (n.nextToken().text.equals("o"));
        assert (n.nextToken().text.equals(","));
        assert (n.nextToken().text.equals(""));
        assert (n.nextToken().type == TokenType.EOL);
    }


    public static void main(String[] args) {
        test();

    }
}
