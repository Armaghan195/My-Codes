package task4_5;

import java.awt.Color;

public class Fox extends Animal {
    public Fox(int initialAge) {
        super("Fox", 0); // Animal initializes age to 0
        setAge(initialAge); // use mutator to set desired age
    }

    @Override
    public int getBreedingAge() {
        return 6; // Foxes breed at age 6
    }

    public static Color getColor() {
        return Color.ORANGE;
    }
}
