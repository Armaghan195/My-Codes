package task2_3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for creating the animal population and defining per-type colors.
 * This class is intentionally coupled to concrete animal classes.
 */
public class PopulationGenerator {

    /**
     * Create an initial population of animals.
     * For this simple demo, we generate one fox and one rabbit.
     */
    public List<Animal> createInitialPopulation() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Fox(1));
        animals.add(new Rabbit(0));
        return animals;
    }

    /**
     * Create an initial population sized for a field.
     * Starts with more rabbits than foxes, both capped by capacity.
     */
    public List<Animal> createInitialPopulation(Field field) {
        int capacity = field.getCapacity();
        int initialRabbits = Math.max(2, Math.min(capacity / 4, 20));
        int initialFoxes = Math.max(1, Math.min(capacity / 12, 8));
        List<Animal> animals = new ArrayList<>(initialRabbits + initialFoxes);
        for (int i = 0; i < initialRabbits && animals.size() < capacity; i++) {
            animals.add(new Rabbit(0));
        }
        for (int i = 0; i < initialFoxes && animals.size() < capacity; i++) {
            animals.add(new Fox(1));
        }
        return animals;
    }

    /**
     * Get the display color for a given animal type.
     */
    public Color getColorFor(Class<? extends Animal> animalType) {
        if (animalType == Fox.class) {
            return Fox.getColor();
        }
        if (animalType == Rabbit.class) {
            return Rabbit.getColor();
        }
        return Color.BLACK; 
        
    }
}
