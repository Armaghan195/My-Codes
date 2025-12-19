
package task6_7;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PopulationGenerator {

    public List<Animal> createInitialPopulation() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Fox(1));
        animals.add(new Rabbit(0));
        return animals;
    }

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
