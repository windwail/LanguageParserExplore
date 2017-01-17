package ru.neirojet;

import ru.neirojet.ast.NJNode;
import ru.neirojet.ast.TokenType;

/**
 * Created by icetsuk on 09.01.17.
 */
public class Main {

    public static void main(String[] args) {


        /*
        String btn = "int xval;";
        NJNode n = new NJNode(btn);
        n.splitTokensByLevel();
        //n.calculateValue();

        btn = "xval = 5 + 5;";
        n = new NJNode(btn);
        n.splitTokensByLevel();
        n.calculateValue();

        System.out.println(n.getValue().getValue());
        *
        */


        String btn = "1 + 2 + 3 - 3 - 2 - 1;";


        NJNode n = new NJNode(btn);
        n.splitTokensByLevel();
        n.calculateValue();
        n.printNodes();

        System.out.println(n.getValue().getValue());


    }
}
