package ru.neirojet.operators;

import ru.neirojet.ast.TokenType;

/**
 * Created by schukanov on 17.01.17.
 */
public class RightToLeftOperator extends Operator {
    public RightToLeftOperator(String text, TokenType type, Integer level) {
        super(text, type, level);
    }
}
