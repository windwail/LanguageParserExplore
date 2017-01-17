package ru.neirojet.variables.widgets;

import lombok.Getter;
import lombok.Setter;
import ru.neirojet.variables.Container;

/**
 * Created by icetusk on 15.01.17.
 */   @Getter
@Setter
public class Label implements Container {


        private String text;
        private String color;
}
