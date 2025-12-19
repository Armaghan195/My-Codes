package task2_3;

import java.util.Random;

public class Animal {
    private final String name;
    private int age;
    private final Random rng;

    public Animal(String name, int initialAge) {
        this.name = name;
        this.age = initialAge;
        this.rng = Randomizer.getRandom();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void grow() {
        int increment = rng.nextInt(3); // 0, 1, or 2 years of growth
        age += increment;
    }
}
