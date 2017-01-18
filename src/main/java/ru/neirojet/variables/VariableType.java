package ru.neirojet.variables;

import ru.neirojet.variables.widgets.Button;
import ru.neirojet.variables.widgets.Input;
import ru.neirojet.variables.widgets.Label;
import ru.neirojet.variables.widgets.Widget;

import java.math.BigDecimal;
import java.util.HashSet;

/**
 * Created by icetusk on 15.01.17.
 */
public enum VariableType {
    BOOLEAN("bool", Boolean.class),
    STRING("string", String.class),
    INTEGER("int", BigDecimal.class),
    BUTTON("button", Button.class),
    LABEL("label", Label.class),
    INPUT("input", Input.class),
    WIDGET("widget", Widget.class),
    FUNCTION("function", Runnable.class);

    private String text;
    private Class clazz;

    public static HashSet<Class> classes = new HashSet<>();

    public static boolean contains(Class clazz) {
        for (VariableType t : VariableType.values()) {
            if (t.clazz.equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    public static VariableType getType(Class clazz) {
        for (VariableType t : VariableType.values()) {
            if (t.clazz.equals(clazz)) {
                return t;
            }
        }
        return null;
    }

    public static VariableType fromDecl(String text) {
        for (VariableType t : VariableType.values()) {
            if (t.text.equals(text)) {
                return t;
            }
        }
        return null;
    }

    VariableType(String text, Class clazz) {
        this.text = text;
        this.clazz = clazz;
    }

    public String getText() {
        return text;
    }

    public Class getClazz() {
        return clazz;
    }

    public static HashSet<Class> getClasses() {
        return classes;
    }
}

