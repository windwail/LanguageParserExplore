package ru.neirojet.ast;

import ru.neirojet.variables.Variable;
import ru.neirojet.variables.VariableType;
import ru.neirojet.variables.widgets.Button;
import ru.neirojet.variables.widgets.Input;
import ru.neirojet.variables.widgets.Label;
import ru.neirojet.variables.widgets.Widget;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by icetusk on 14.01.17.
 */


public class Enviroment {

    private Enviroment() {
        defineVariable("int", "test");
        setVariable("test", "123");

        Variable<Label> l = new Variable<Label>(VariableType.LABEL, new Label());
        vars.put("label1", l);
        Variable<Button> b = new Variable<Button>(VariableType.BUTTON, new Button());
        vars.put("button1", b);
        Variable<Input> i = new Variable<Input>(VariableType.INPUT, new Input());
        vars.put("input1", i);
        Widget w = new Widget(b.getValue(), l.getValue(), i.getValue());
        //Variable<Widget> wv = new Variable<>(VariableType.WIDGET, w);
        //vars.put("widget1", wv);
    }

    private HashMap<String, Variable> vars = new HashMap<>();

    private static final Enviroment INSTANCE = new Enviroment();

    public static Enviroment instance() {
        return INSTANCE;
    }

    public void defineVariable(String type, String name) {
        if (vars.containsKey(name)) {
            throw new RuntimeException("Variable already defined:" + name);
        }

        VariableType t = VariableType.fromDecl(type);
        Variable v;

        switch (t) {
            case BOOLEAN:
                v = new Variable<Boolean>(t, false);
                break;
            case STRING:
                v = new Variable<String>(t, "");
                break;
            case INTEGER:
                v = new Variable<BigInteger>(t, new BigInteger("0"));
                break;
            default:
                throw new RuntimeException("Unknown type!");
        }

        vars.put(name, v);
    }

    public Variable getVariable(String name) {

        return vars.get(name);
    }


    public void setVariable(String name, String value) {

        Variable v = vars.get(name);

        if (v == null) throw new RuntimeException("No such variable: " + name);

        switch (v.getType()) {
            case BOOLEAN:
                v.setValue(Boolean.valueOf(value));
                break;
            case STRING:
                v.setValue(value);
                break;
            case INTEGER:
                v.setValue(new BigInteger(value));
                break;
            default:
                throw new RuntimeException("Unknown type!");
        }


    }

    public Variable callFunc(String name, Variable... args) {

        if (name.equals("pirnt")) {
            System.out.println(args);
        }

        return null;
    }

}
