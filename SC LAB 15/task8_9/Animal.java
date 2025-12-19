package task8_9;

import java.util.Random;

public abstract class Animal implements Actor {
    private final String name;
    private int age;
    private final Random rng;

    public Animal(String name, int initialAge) {
        this.name = name;
        this.age = 0; // initialize to zero per requirement
        this.rng = Randomizer.getRandom();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void grow() {
        int increment = rng.nextInt(3); // 0, 1, or 2 years of growth
        age += increment;
    }

    /**
     * Return the maximum age for this animal.
     * @return The maximum age this animal can live.
     */
    abstract protected int getMaxAge();

    /**
     * Increment the age of the animal, respecting the maximum age limit.
     */
    public void incrementAge() {
        int increment = rng.nextInt(3); // 0, 1, or 2 years of growth
        age = Math.min(age + increment, getMaxAge());
    }

    /**
     * Return the breeding age of this animal.
     * @return The breeding age of this animal.
     */
    abstract protected int getBreedingAge();

    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed
     */
    public boolean canBreed() {
        return age >= getBreedingAge();
    }

    /**
     * Produce a new animal of the same species.
     * @return A newly born animal of the same type.
     */
    abstract public Animal breed();
}
