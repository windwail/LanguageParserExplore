package ru.neirojet.ast;

import ru.neirojet.operators.Operator;

/**
 * Created by icetsuk on 13.01.17.
 */
public class Token {
    public TokenType type;
    public String text;
    public boolean isOperator;
    public Operator operator;

    public Token(String text, TokenType type) {
        this.type = type;
        this.text = text;
    }

    public Token(Operator op) {
        this.isOperator = true;
        this.operator = op;
        this.type = op.getType();
        this.text = op.getText();
    }

    public Token(String text, Operator op) {
        this.isOperator = true;
        this.operator = op;
        this.type = op.getType();
        this.text = text;
    }

    @Override
    public String toString() {
        return "{"
                + text +
                '}';
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isOperator() {
        return isOperator;
    }

    public void setOperator(boolean operator) {
        isOperator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
