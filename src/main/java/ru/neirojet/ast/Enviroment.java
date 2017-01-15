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


    public void put(String name, Variable v) {
        vars.put(name, v);
    }

    public Variable get(String name) {
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
