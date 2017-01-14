import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

/**
 * Created by icetsuk on 13.01.17.
 */
@Setter
@Getter
public class Operator  {

    private String text;
    private TokenType type;
    private Integer level;
    private Token[] tokensInvolved;

    public Operator(String text, TokenType type, Integer level) {
        this.text = text;
        this.type = type;
        this.level = level;
    }

    public boolean detect(Token t, LinkedList<Token> tokens) {
        return t.text.equals(text);
    }

    /**
     *
     * Когда находим оператор - эта функци происохдит разбиение для формирования
     * дерева
     *
     * a+b+c => split(2,{a,b,c},node{a+b+c})
     *
     *  +
     * | \
     * a b+c
     *
     * Не забываем мутировать текующую ноду чтобы в ней был один токен.
     *
     * @param indx
     * @param tokens
     * @param node
     * @return
     */
    public LinkedList<NJNode> split(int indx, LinkedList<Token> tokens, NJNode node) {
        LinkedList<Token> left = new LinkedList<>();
        LinkedList<Token> middle = new LinkedList<>();
        LinkedList<Token> right = new LinkedList<>();

        for(int i=0; i<tokens.size(); i++) {
            if(i < indx) {
                left.addLast(tokens.get(i));
            } else if (i == indx) {
                middle.addLast(tokens.get(i));
            } else {
                right.addLast(tokens.get(i));
            }
        }

        LinkedList<NJNode> result = new LinkedList<NJNode>();

        NJNode lft = new NJNode(left, node);
        //lft.printTokens("new left node");

        NJNode rgt = new NJNode(right, node);
        //rgt.printTokens("new right node");

        node.mutate(tokens.get(indx));

        result.add(lft);
        result.add(rgt);

        return result;
    }


}
