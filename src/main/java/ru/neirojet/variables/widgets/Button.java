package ru.neirojet.variables.widgets;

import lombok.Getter;
import lombok.Setter;
import ru.neirojet.variables.Container;

@Getter
@Setter
public class Button implements Container {
    private String text;
    private String color;
}
