package ru.neirojet.variables.widgets;

import ru.neirojet.variables.Container;

/**
 * Created by icetusk on 15.01.17.
 */
public class Label implements Container {


        private String text;
        private String color;

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public String getColor() {
                return color;
        }

        public void setColor(String color) {
                this.color = color;
        }
}
