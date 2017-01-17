package ru.neirojet;

import org.junit.Test;
import ru.neirojet.ast.NJNode;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by schukanov on 17.01.17.
 */
public class OpAddingTest extends NeirojetTest {


    @Test
    public void test() {
        assertNotNull(env.getVariable("test"));

        String btn = "test = 1 + 2 + 3 - 3 - 2 - 1;";
        NJNode n = new NJNode(btn);
        n.splitTokensByLevel();
        n.calculateValue();
        n.printNodes();

        System.out.println("yyyya");

        //assertTrue(env.get("test").getValue().equals(new BigDecimal("0")));
    }

}
