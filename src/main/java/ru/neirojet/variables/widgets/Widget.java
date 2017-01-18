package ru.neirojet.variables.widgets;

import ru.neirojet.variables.Container;

public class Widget implements Container {
    private Button button1;
    private Label label1;
    private Input input1;

    public void action() {
        System.out.println("Action called");
    }

    public Widget(Button button1, Label label1, Input input1) {
        this.button1 = button1;
        this.label1 = label1;
        this.input1 = input1;
    }

    public Button getButton1() {
        return button1;
    }

    public void setButton1(Button button1) {
        this.button1 = button1;
    }

    public Label getLabel1() {
        return label1;
    }

    public void setLabel1(Label label1) {
        this.label1 = label1;
    }

    public Input getInput1() {
        return input1;
    }

    public void setInput1(Input input1) {
        this.input1 = input1;
    }
}
