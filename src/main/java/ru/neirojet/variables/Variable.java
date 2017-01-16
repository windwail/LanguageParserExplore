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
    String name;

    private HashMap<String, Variable> properties = new HashMap<>();

    public Variable getProperty(String name) {
        return properties.get(name);
    }

    public void setProperty(String name, Object value) {
        properties.get(name).value = value;
    }

    public Variable(VariableType t, E e, String name) {

        this.type = t;
        this.value = e;
        this.name = name;

        if(e instanceof Container) {
            try {
                Field[] attributes = t.getClazz().getDeclaredFields();
                for (Field field : attributes) {
                    if (VariableType.contains(field.getType())) {
                        field.setAccessible(true);
                        Object value = e != null ? field.get(e) : null;
                        Variable v = new Variable(VariableType.getType(field.getType()), value, field.getName());
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