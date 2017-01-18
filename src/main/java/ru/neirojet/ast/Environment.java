package ru.neirojet.ast;

import ru.neirojet.variables.Variable;
import ru.neirojet.variables.VariableType;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by icetusk on 14.01.17.
 */

public class Environment {

    private Environment() {

    }

    private HashMap<String, Variable> vars = new HashMap<>();

    private static final Environment INSTANCE = new Environment();

    public static Environment instance() {
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
                v = new Variable<Boolean>(t, false, name);
                break;
            case STRING:
                v = new Variable<String>(t, "", name);
                break;
            case INTEGER:
                v = new Variable<BigDecimal>(t, new BigDecimal("0"), name);
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

    public void put(Variable v) {
        vars.put(v.getName(), v);
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
                v.setValue(new BigDecimal(value));
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

    public HashMap<String, Variable> getVars() {
        return vars;
    }

    public void setVars(HashMap<String, Variable> vars) {
        this.vars = vars;
    }
}
