import java.util.LinkedList;

/**
 * Created by icetusk on 14.01.17.
 */
public class OperatorArguments extends Operator {
    public OperatorArguments(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    @Override
    public boolean detect(Token t, LinkedList<Token> tokens) {
        if(t.type == TokenType.CALLARGUMENTS) {
            return true;
        }

        return false;
    }

    @Override
    public LinkedList<NJNode> split(int indx, LinkedList<Token> tokens, NJNode node) {
        System.out.println("SPLITT OPERATOR CALL");

        LinkedList<NJNode> result = new LinkedList<>();

        for(Token t: tokens) {
            System.out.println(t);
        }

        return result;
    }
}
