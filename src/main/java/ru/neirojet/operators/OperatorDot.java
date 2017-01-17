package ru.neirojet.operators;

import ru.neirojet.ast.Environment;
import ru.neirojet.ast.NJNode;
import ru.neirojet.ast.Token;
import ru.neirojet.ast.TokenType;
import ru.neirojet.variables.Variable;

import java.util.LinkedList;

/**
 * Created by icetusk on 14.01.17.
 */
public class OperatorDot extends Operator {
    public OperatorDot(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    private Environment env = Environment.instance();

    @Override
    public Variable calculateValue(NJNode node) {

        LinkedList<Token> tokens = node.getTokens();

        if(node.getType() == TokenType.SYMBOLIC) {
            System.out.println("Symbolic value");

            Variable current = null;

            for(int i=0; i<tokens.size(); i++) {

                Token t = tokens.get(i);

                if(t.type == TokenType.SYMBOLIC) {
                    if(current == null) {
                        current = env.getVariable(t.text);
                    } else {
                        current = current.getProperty(t.text);
                    }
                }
            }

            return current;

        } else if(node.getType() == TokenType.CALL) {
            throw new RuntimeException("NOT IMPLEMENTED YET!");
        } else {
            throw new RuntimeException("Incorrect node type for dot operator: " + node.getType());
        }
    }

    @Override
    public LinkedList<NJNode> split(int indx, LinkedList<Token> tokens, NJNode node) {

        if(indx+2 < tokens.size() && tokens.get(indx+2).type == TokenType.DOT) {
            return split(indx+2, tokens, node);
        }

        LinkedList<NJNode> result = new LinkedList<>();
        LinkedList<Token> tkns = new LinkedList<>();


        // +1 потому что нужно захватить еще следующий параметр
        for(int i=indx+2; i<tokens.size(); i++) {
            NJNode n = new NJNode(tokens.get(i),node);
            result.add(n);
        }

        // +1 потому что нужно захватить еще следующий параметр
        for(int i=0; i<=indx+1; i++) {
            tkns.add(tokens.get(i));
        }

        node.mutate(tkns, this);

        return result;
    }
}
