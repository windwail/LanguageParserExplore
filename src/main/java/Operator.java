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

    public LinkedList<Token>[] split(int indx, LinkedList<Token> tokens) {
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

        return  new LinkedList[] {left, middle, right};
    }


}
