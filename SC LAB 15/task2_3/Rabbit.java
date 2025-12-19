package task2_3;

import java.awt.Color;

public class Rabbit extends Animal {
    public Rabbit(int initialAge) {
        super("Rabbit", initialAge);
    }

    public static Color getColor() {
        return Color.GRAY;
    }
}
