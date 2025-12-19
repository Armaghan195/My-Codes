package task8_9;

import java.awt.Color;

public class Fox extends Animal {
    public Fox(int initialAge) {
        super("Fox", 0); // Animal initializes age to 0
        setAge(initialAge); // use mutator to set desired age
    }

    @Override
    protected int getMaxAge() {
        return 150; // Foxes have a max lifespan of 150 years
    }

    @Override
    public int getBreedingAge() {
        return 6; // Foxes breed at age 6
    }

    @Override
    public Animal breed() {
        return new Fox(0); // New fox born at age 0
    }

    public static Color getColor() {
        return Color.ORANGE;
    }
}
