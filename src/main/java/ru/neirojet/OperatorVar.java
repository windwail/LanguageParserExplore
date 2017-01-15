package ru.neirojet;

import java.util.LinkedList;

/**
 * Created by icetusk on 14.01.17.
 */
public class OperatorVar extends Operator {
    public OperatorVar(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    @Override
    public LinkedList<NJNode> split(int indx, LinkedList<Token> tokens, NJNode node) {
        LinkedList<NJNode> result = new LinkedList<>();

        assert (tokens.size() == 2);

        NJNode nd = new NJNode(tokens.get(1), node);
        nd.getTokens().get(0).setType(TokenType.VARNAME);
        result.add(nd);

        node.mutate(tokens.get(0), this);

        return result;
    }
}
