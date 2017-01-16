package ru.neirojet.operators;

import ru.neirojet.ast.NJNode;
import ru.neirojet.ast.TokenType;
import ru.neirojet.variables.Variable;

/**
 * Created by icetusk on 15.01.17.
 */
public class OperatorAssign extends Operator {
    public OperatorAssign(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    @Override
    public Variable calculateValue(NJNode node) {

        assert(node.getChildren().size() == 2);

        Variable v1 = node.getChildren().get(0).getValue();
        Variable v2 = node.getChildren().get(1).getValue();

        if(v1.getType() == v2.getType()) {
            v1.setValue(v2.getValue());
        } else {
            throw new RuntimeException("Variable type:"+v1.getType()+" rvalue type:"+v2.getType());
        }

        return v1;
    }
}
