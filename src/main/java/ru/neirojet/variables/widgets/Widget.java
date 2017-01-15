package ru.neirojet.variables.widgets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.neirojet.Enviroment;
import ru.neirojet.variables.Container;

@Getter
@Setter
@AllArgsConstructor
public class Widget implements Container {
    private Button button1;
    private Label label1;
    private Input input1;

    public void action() {
        System.out.println("Action called");
    }
}
