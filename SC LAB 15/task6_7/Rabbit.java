package task6_7;

import java.awt.Color;

public class Rabbit extends Animal {
    public Rabbit(int initialAge) {
        super("Rabbit", 0); // Animal initializes age to 0
        setAge(initialAge); // use mutator to set desired age
    }

    @Override
    protected int getMaxAge() {
        return 40; // Rabbits have a max lifespan of 40 years
    }

    @Override
    public int getBreedingAge() {
        return 4; // Rabbits breed at age 4
    }

    @Override
    public Animal breed() {
        return new Rabbit(0); // New rabbit born at age 0
    }

    public static Color getColor() {
        return Color.GRAY;
    }
}
