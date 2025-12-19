package task4_5;

import java.awt.Color;

public class Rabbit extends Animal {
    public Rabbit(int initialAge) {
        super("Rabbit", 0); // Animal initializes age to 0
        setAge(initialAge); // use mutator to set desired age
    }

    @Override
    public int getBreedingAge() {
        return 4; // Rabbits breed at age 4
    }

    public static Color getColor() {
        return Color.GRAY;
    }
}
