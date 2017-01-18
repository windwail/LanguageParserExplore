package ru.neirojet.variables.widgets;

import ru.neirojet.variables.Container;

public class Input implements Container {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Input(String text) {
        this.text = text;
    }

    public Input() {
    }
}
