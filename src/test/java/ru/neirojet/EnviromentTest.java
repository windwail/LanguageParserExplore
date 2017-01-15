package ru.neirojet;

import org.junit.Test;
import ru.neirojet.ast.Enviroment;
import ru.neirojet.variables.Variable;
import ru.neirojet.variables.VariableType;
import ru.neirojet.variables.widgets.Button;
import ru.neirojet.variables.widgets.Input;
import ru.neirojet.variables.widgets.Label;
import ru.neirojet.variables.widgets.Widget;

import static org.junit.Assert.*;

/**
 * Created by icetusk on 15.01.17.
 */
public class EnviromentTest {

    Enviroment env = Enviroment.instance();

    @org.junit.Before
    public void setUp() throws Exception {
        env.defineVariable("int", "test");
        env.setVariable("test", "123");

        Variable<Label> l = new Variable<>(VariableType.LABEL, new Label());
        env.put("label1", l);
        Variable<Button> b = new Variable<>(VariableType.BUTTON, new Button());
        env.put("button1", b);
        Variable<Input> i = new Variable<>(VariableType.INPUT, new Input());
        env.put("input1", i);
        Widget w = new Widget(b.getValue(), l.getValue(), i.getValue());
        Variable<Widget> wv = new Variable<>(VariableType.WIDGET, w);
        env.put("widget1", wv);
    }

    @Test
    public void testEnv() throws Exception {

    }

}