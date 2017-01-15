package ru.neirojet.ast;

import lombok.Getter;
import lombok.Setter;
import ru.neirojet.operators.Operator;

/**
 * Created by icetsuk on 13.01.17.
 */
@Getter
@Setter
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
}
