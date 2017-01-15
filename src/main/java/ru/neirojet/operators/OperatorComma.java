package ru.neirojet.operators;

import ru.neirojet.ast.NJNode;
import ru.neirojet.ast.Token;
import ru.neirojet.ast.TokenType;

import java.util.LinkedList;

/**
 * Created by icetusk on 14.01.17.
 */
public class OperatorComma extends Operator {
    public OperatorComma(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    @Override
    public LinkedList<NJNode> split(int indx, LinkedList<Token> tokens, NJNode node) {

        LinkedList<NJNode> result = new LinkedList<>();
        LinkedList<Token> tkns = new LinkedList<>();

        for(Token t: tokens) {
            if(t.type == TokenType.COMMA) {
                NJNode no = new NJNode(tkns,node);
                result.add(no);
                tkns = new LinkedList<>();
            } else {
                tkns.add(t);
            }
        }

        if(!tkns.isEmpty()) {
            NJNode no = new NJNode(tkns,node);
            result.add(no);
        }

        node.mutate(tokens.get(indx), this);

        return result;
    }

}
