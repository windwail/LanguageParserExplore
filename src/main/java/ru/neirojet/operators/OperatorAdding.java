package ru.neirojet.operators;

import ru.neirojet.ast.NJNode;
import ru.neirojet.ast.Token;
import ru.neirojet.ast.TokenType;
import ru.neirojet.variables.Variable;
import ru.neirojet.variables.VariableType;

import java.math.BigDecimal;

/**
 * Created by schukanov on 16.01.17.
 */
public class OperatorAdding extends RightToLeftOperator{
    public OperatorAdding(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    @Override
    public Variable calculateValue(NJNode node) {

        assert (node.getTokens().size()==1);
        assert (node.getChildren().size()==2);
        assert (node.getChildren().getFirst().getType()==TokenType.NUMBER);
        assert (node.getChildren().getFirst().getValue()!=null);
        assert (node.getChildren().getLast().getValue()!=null);


        Token op = node.getTokens().getFirst();

        Variable<BigDecimal> v1 = node.getChildren().getFirst().getValue();
        Variable<BigDecimal> v2 = node.getChildren().getLast().getValue();

        BigDecimal res = null;
        if(op.getText().equals("+")) {
            res = v1.getValue().add(v2.getValue());
        } else if(op.getText().equals("-")) {
            res = v1.getValue().subtract(v2.getValue());
        }

        return new Variable(VariableType.INTEGER, res, null);

    }
}
