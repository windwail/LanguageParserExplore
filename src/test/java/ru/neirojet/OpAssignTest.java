package ru.neirojet;

import org.junit.Test;
import ru.neirojet.ast.NJNode;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Created by icetusk on 15.01.17.
 */
public class OpAssignTest extends NeirojetTest{

    @Test
    public void test() {
        assertNotNull(env.getVariable("test"));

        String btn = "test = 54;";
        NJNode n = new NJNode(btn);
        n.splitTokensByLevel();
        n.calculateValue();

        assertTrue(env.get("test").getValue().equals(new BigInteger("54")));
    }

    @Test(expected=RuntimeException.class)
    public void testIndexOutOfBoundsException() {
        String btn = "test = 'some';";
        NJNode n = new NJNode(btn);
        n.splitTokensByLevel();
        n.calculateValue();
    }
}
