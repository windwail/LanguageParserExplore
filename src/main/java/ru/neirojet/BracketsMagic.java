package ru.neirojet;

import java.util.LinkedList;

/**
 * Created by icetsuk on 10.01.17.
 */
public class BracketsMagic {

    public static void main(String[] args) {
        String example = "some((1)B((2)(3)))Atrum(aga),(((aga)(in)))";

        LinkedList<Integer> stack = new LinkedList<>();

        StringBuilder sb = new StringBuilder();


        int index = 1;
        int watchdog = 1000;
        int i = 0;

        while(i < example.length()) {
            char co = example.charAt(i);
            if (co == '(') {
                sb = new StringBuilder();
                stack = new LinkedList<>();
                index = i;
                 do{
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
                } while(stack.size()!=0 && watchdog > 0);

                System.out.println(index + ":" + i);
                System.out.println(sb);
                System.out.println(example.substring(i,index));
                i = index;

            }
            i+=1;
        }

    }
}
