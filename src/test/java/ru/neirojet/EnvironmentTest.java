package ru.neirojet;

import org.junit.Test;
import ru.neirojet.variables.Variable;

import static org.junit.Assert.*;

/**
 * Created by icetusk on 15.01.17.
 */
public class EnvironmentTest extends NeirojetTest{


    @Test
    public void testEnv() throws Exception {

        Variable v = env.getVariable("widget1");

        assertNotNull(v);
        assertNotNull(v.getValue());
        assertTrue(v.getProperties().size() == 3);

    }
}
