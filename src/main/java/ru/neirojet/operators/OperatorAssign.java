package ru.neirojet.operators;

import ru.neirojet.ast.NJNode;
import ru.neirojet.ast.TokenType;

/**
 * Created by icetusk on 15.01.17.
 */
public class OperatorAssign extends Operator {
    public OperatorAssign(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    @Override
    public Object calculateValue(NJNode node) {
        return super.calculateValue(node);
    }
}
