package ru.neirojet;

import org.junit.Test;
import ru.neirojet.ast.NJNode;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by schukanov on 18.01.17.
 */
public class OpMultiplyTest extends NeirojetTest{

    @Test
    public void test() {
        assertNotNull(env.getVariable("test"));

        String btn = "test = 1 + 2 * 3 + 6 * 3;";
        NJNode n = new NJNode(btn);
        n.splitTokensByLevel();
        n.calculateValue();
        assertTrue(env.get("test").getValue().equals(new BigDecimal("25")));
    }

}
