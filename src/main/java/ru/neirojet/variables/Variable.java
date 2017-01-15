package ru.neirojet.variables;

import lombok.Getter;
import lombok.Setter;

import javax.xml.ws.BindingType;
import java.lang.reflect.Field;
import java.util.HashMap;

@Getter
@Setter
public class Variable<E> {
    VariableType type;
    E value;

    private HashMap<String, Variable> properties = new HashMap<>();

    public Variable(VariableType t, E e) {

        this.type = t;
        this.value = e;

        if(e instanceof Container) {
            System.out.println("Creating variable: " + t + " value:" + e);
            try {
                Field[] attributes = t.getClazz().getDeclaredFields();
                for (Field field : attributes) {
                    if (VariableType.contains(field.getType())) {
                        field.setAccessible(true);
                        Object value = e != null ? field.get(e) : null;
                        Variable v = new Variable(VariableType.getType(field.getType()), value);
                        properties.put(field.getName(), v);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

    }
}