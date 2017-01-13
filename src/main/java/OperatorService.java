
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by icetsuk on 13.01.17.
 */
public class OperatorService {

    public static final int UNKNOWN = 1000;

    public static final int MAX_LEVEL = 15;

    private static final OperatorService INSTANCE = new OperatorService();

    public  HashMap<Integer, HashSet<Operator>> operators = new HashMap<>();

    public  HashMap<String, Operator> opsByText = new HashMap<>();

    private OperatorService() {

        for(int i=1; i<16; i++) {
            operators.put(i,new HashSet<>());
        }
        operators.put(UNKNOWN,new HashSet<>());

        build("=",TokenType.BINARY, 1);
        build("+=",TokenType.BINARY, 1);
        build("-=",TokenType.BINARY, 1);
        build("*=",TokenType.BINARY, 1);
        build("/=",TokenType.BINARY, 1);
        build("%=",TokenType.BINARY, 1);

        build("||",TokenType.BINARY, 3);

        build("&&",TokenType.BINARY, 4);

        build("|",TokenType.BINARY, 5);

        build("^",TokenType.BINARY, 6);

        build("&",TokenType.BINARY, 7);

        build("==",TokenType.BINARY, 8);
        build("!=",TokenType.BINARY, 8);

        build("<",TokenType.BINARY, 9);
        build("<=",TokenType.BINARY, 9);
        build(">",TokenType.BINARY, 9);
        build(">=",TokenType.BINARY, 9);

        build("<<",TokenType.BINARY, 10);
        build(">>",TokenType.BINARY, 10);
        build(">>>",TokenType.BINARY, 10);

        build("-",TokenType.BINARY, 11);
        build("+",TokenType.BINARY, 11);

        build("*",TokenType.BINARY,12 );
        build("/",TokenType.BINARY,12 );
        build("%",TokenType.BINARY, 12);

        build("++",TokenType.BINARY, 13);
        build("--",TokenType.BINARY, 13);
        build("!",TokenType.UNARY, 13); // Unary minus
        build("~",TokenType.BINARY, 13); // Unary minus

        build("",TokenType.BINARY, 14);

        build(".",TokenType.BINARY,15 );

        build("(",TokenType.OPEN_BRACKET );
        build(")",TokenType.OPEN_BRACKET );

        build(";",TokenType.EOL );
        build(" ", TokenType.BLANK);
        build(",", TokenType.COMMA);

        build("bool", TokenType.TYPE);
        build("int", TokenType.TYPE);
        build("string", TokenType.TYPE);

        build("<call>", TokenType.CALL);
        build("<call arguments>", TokenType.CALLARGUMENTS);
    }

    public void build(String text, TokenType type) {
        build(text,type, UNKNOWN,Operator.class);
    }

    public void build(String text, TokenType type, Integer level) {
        build(text,type,level,Operator.class);
    }

    public void build(String text, TokenType type, Integer level, Class<? extends Operator> clazz) {
        try {
            Constructor ctor = clazz.getConstructor(String.class, TokenType.class, Integer.class);
            Operator o = (Operator) ctor.newInstance(new Object[] {text, type, level });
            operators.get(level).add(o);

            if(opsByText.containsKey(o.getText())) {
                throw new RuntimeException("Duplicate operator: "+o.getText());
            }

            opsByText.put(o.getText(),o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int detectOpLevel(LinkedList<Token> tokens) {
        for(int l=1; l<=MAX_LEVEL; l++) {
            for (Token t : tokens) {
                if(inLevel(l, t.text)) {
                    return l;
                };
            }
        }
        return -1;
    }

    public boolean inLevel(int level, String text) {
        if(opsByText.containsKey(text)) {
            return opsByText.get(text).getLevel() == level;
        }
        return false;
    }

    public boolean isOperator(String text) {
        return opsByText.containsKey(text);
    }

    public Operator getOperator(String text) {
        return opsByText.get(text);
    }

    public static OperatorService instance() {
        return INSTANCE;
    }
}
