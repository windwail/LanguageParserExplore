import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by icetsuk on 10.01.17.
 */
public class BracketsMagic {

    public static void main(String[] args) {
        String example = "((1)B((2)(3)))A";

        LinkedList<Integer> stack = new LinkedList<>();

        StringBuilder sb = new StringBuilder();

        stack.push(0);
        int index = 1;
        int watchdog = 1000;
        int i = 0;

        while(i < example.length()) {

        }




        while(stack.size()!=0 && watchdog > 0) {

            watchdog-=1;

            char c = example.charAt(index);

            if(c == '(') {
                stack.push(index);
            }

            if(c == ')') {
                stack.pop();
            }

            sb.append(c);

            index+=1;
        }

        System.out.println(sb);

    }
}
