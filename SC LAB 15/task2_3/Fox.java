package task2_3;

import java.awt.Color;

public class Fox extends Animal {
    public Fox(int initialAge) {
        super("Fox", initialAge);
    }

    public static Color getColor() {
        return Color.ORANGE;
    }
}
