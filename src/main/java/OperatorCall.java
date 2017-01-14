import java.util.LinkedList;

/**
 * Created by icetusk on 14.01.17.
 */
public class OperatorCall extends Operator {
    public OperatorCall(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    @Override
    public boolean detect(Token t, LinkedList<Token> tokens) {
        if(t.type == TokenType.CALL) {
            return true;
        }

        return false;
    }

    @Override
    public LinkedList<NJNode> split(int indx, LinkedList<Token> tokens, NJNode node) {

        LinkedList<NJNode> result = new LinkedList<>();
        assert (tokens.size() == 2);
        result.add(new NJNode(tokens.get(1), node));

        return result;
    }


}
