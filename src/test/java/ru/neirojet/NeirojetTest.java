package ru.neirojet;

import ru.neirojet.ast.Environment;
import ru.neirojet.variables.Variable;
import ru.neirojet.variables.VariableType;
import ru.neirojet.variables.widgets.Button;
import ru.neirojet.variables.widgets.Input;
import ru.neirojet.variables.widgets.Label;
import ru.neirojet.variables.widgets.Widget;

/**
 * Created by icetusk on 15.01.17.
 */
public class NeirojetTest {

    Environment env ;

    @org.junit.Before
    public void setUp() throws Exception {
        env = Environment.instance();
        env.defineVariable("int", "test");
        env.setVariable("test", "123");

        Variable<Label> l = new Variable<>(VariableType.LABEL, new Label(), "label1");
        env.put("label1", l);
        Variable<Button> b = new Variable<>(VariableType.BUTTON, new Button(), "button1");
        env.put("button1", b);
        Variable<Input> i = new Variable<>(VariableType.INPUT, new Input(), "input1");
        env.put("input1", i);
        Widget w = new Widget(b.getValue(), l.getValue(), i.getValue());
        Variable<Widget> wv = new Variable<>(VariableType.WIDGET, w, "widget1");
        env.put("widget1", wv);
    }

    @org.junit.After
    public void destroy() throws Exception {
        env.getVars().clear();
    }
}
