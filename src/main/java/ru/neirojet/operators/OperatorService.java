package ru.neirojet.operators;

import ru.neirojet.ast.Token;
import ru.neirojet.ast.TokenType;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by icetsuk on 13.01.17.
 */
public class OperatorService {

    public static final int UNKNOWN = 1000;

    public static final int MAX_LEVEL = 15;

    private static final OperatorService INSTANCE = new OperatorService();

    public HashMap<Integer, HashSet<Operator>> operators = new HashMap<>();

    public HashMap<String, Operator> opsByText = new HashMap<>();

    private OperatorService() {

        for (int i = 1; i < 16; i++) {
            operators.put(i, new HashSet<>());
        }
        operators.put(UNKNOWN, new HashSet<>());

        build("=", TokenType.BINARY, 1, OperatorAssign.class);
        build(",", TokenType.COMMA, 1, OperatorComma.class);
        build("+=", TokenType.BINARY, 1);
        build("-=", TokenType.BINARY, 1);
        build("*=", TokenType.BINARY, 1);
        build("/=", TokenType.BINARY, 1);
        build("%=", TokenType.BINARY, 1);

        build("||", TokenType.BINARY, 3);

        build("&&", TokenType.BINARY, 4);

        build("|", TokenType.BINARY, 5);

        build("^", TokenType.BINARY, 6);

        build("&", TokenType.BINARY, 7);

        build("==", TokenType.BINARY, 8);
        build("!=", TokenType.BINARY, 8);

        build("<", TokenType.BINARY, 9);
        build("<=", TokenType.BINARY, 9);
        build(">", TokenType.BINARY, 9);
        build(">=", TokenType.BINARY, 9);

        build("<<", TokenType.BINARY, 10);
        build(">>", TokenType.BINARY, 10);
        build(">>>", TokenType.BINARY, 10);

        build("-", TokenType.BINARY, 11, OperatorAdding.class);
        build("+", TokenType.BINARY, 11, OperatorAdding.class);

        build("*", TokenType.BINARY, 12);
        build("/", TokenType.BINARY, 12);
        build("%", TokenType.BINARY, 12);

        build("++", TokenType.BINARY, 13);
        build("--", TokenType.BINARY, 13);
        build("!", TokenType.UNARY, 13); // Unary minus
        build("~", TokenType.BINARY, 13); // Unary minus

        build(".", TokenType.DOT, 15, OperatorDot.class);

        build("(", TokenType.OPEN_BRACKET);
        build(")", TokenType.OPEN_BRACKET);

        build(";", TokenType.EOL);
        build(" ", TokenType.BLANK);


        build("bool", TokenType.TYPE, OperatorVar.class);
        build("int", TokenType.TYPE, OperatorVar.class);
        build("string", TokenType.TYPE, OperatorVar.class);

        build(TokenType.CALL, OperatorCall.class);
        build(TokenType.CALLARGUMENTS, OperatorArguments.class);
    }

    public void build(TokenType type) {
        build(UUID.randomUUID().toString(), type, UNKNOWN);
    }


    public void build(TokenType type, Class<? extends Operator> clazz) {
        build(UUID.randomUUID().toString(), type, UNKNOWN, clazz);
    }

    public void build(String text, TokenType type) {
        build(text, type, UNKNOWN, Operator.class);
    }

    public void build(String text, TokenType type, Class<? extends Operator> clazz) {
        build(text, type, UNKNOWN, clazz);
    }

    public void build(String text, TokenType type, Integer level) {
        build(text, type, level, Operator.class);
    }

    public void build(String text, TokenType type, Integer level, Class<? extends Operator> clazz) {
        try {
            Constructor ctor = clazz.getConstructor(String.class, TokenType.class, Integer.class);
            Operator o = (Operator) ctor.newInstance(new Object[]{text, type, level});
            operators.get(level).add(o);

            if (!o.getText().isEmpty() && opsByText.containsKey(o.getText())) {
                throw new RuntimeException("Duplicate operator: " + o.getText());
            }

            opsByText.put(o.getText(), o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public int detectOpLevel(LinkedList<Token> tokens) {
        int min_layer = UNKNOWN;
        for (Token t : tokens) {
            for (Operator o : opsByText.values()) {
                if (o.detect(t, tokens)) {
                    if (o.getLevel() < min_layer) {
                        min_layer = o.getLevel();
                    }
                }
            }
        }
        return min_layer;
    }

    public boolean inLevel(int level, Token to, LinkedList<Token> tokens) {
        for (Operator o : opsByText.values()) {
            if (o.detect(to, tokens)) {
                if (o.getLevel() == level) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOperator(String text) {
        return opsByText.containsKey(text);
    }

    public Operator getOperator(String text) {
        return opsByText.get(text);
    }

    public Operator getOperator(Token token, LinkedList<Token> tokens) {
        Operator result = getOperator(token.text);

        if(result == null) {
            for(Operator o: opsByText.values()) {
                if(o.detect(token, tokens)) {
                    return o;
                }
            }
        }

        return result;
    }

    public static OperatorService instance() {
        return INSTANCE;
    }
}
