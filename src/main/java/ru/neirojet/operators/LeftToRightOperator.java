package ru.neirojet.operators;

import ru.neirojet.ast.TokenType;

/**
 * Created by schukanov on 17.01.17.
 */
public class LeftToRightOperator extends Operator {
    public LeftToRightOperator(String text, TokenType type, Integer level) {
        super(text, type, level);
    }
}
