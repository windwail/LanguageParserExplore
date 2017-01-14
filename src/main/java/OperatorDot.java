import java.util.LinkedList;

/**
 * Created by icetusk on 14.01.17.
 */
public class OperatorDot extends Operator {
    public OperatorDot(String text, TokenType type, Integer level) {
        super(text, type, level);
    }

    @Override
    public LinkedList<NJNode> split(int indx, LinkedList<Token> tokens, NJNode node) {

        if(indx+2 < tokens.size() && tokens.get(indx+2).type == TokenType.DOT) {
            return split(indx+2, tokens, node);
        }

        LinkedList<NJNode> result = new LinkedList<>();
        LinkedList<Token> tkns = new LinkedList<>();


        // +1 потому что нужно захватить еще следующий параметр
        for(int i=indx+2; i<tokens.size(); i++) {
            NJNode n = new NJNode(tokens.get(i),node);
            result.add(n);
        }

        // +1 потому что нужно захватить еще следующий параметр
        for(int i=0; i<=indx+1; i++) {
            tkns.add(tokens.get(i));
        }

        node.mutate(tkns);

        return result;
    }
}
