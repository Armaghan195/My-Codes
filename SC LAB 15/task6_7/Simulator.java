package task6_7;

import java.util.List;

public class Simulator {
    private final PopulationGenerator generator;
    private final GraphView graphView = new GraphView(40);

    public Simulator(PopulationGenerator generator) {
        this.generator = generator;
    }

    public static void main(String[] args) {
        Simulator sim = new Simulator(new PopulationGenerator());
        sim.runOnce("First run (shared, seeded)", true, true);
        sim.runOnce("Second run after reset (identical to first)", true, true);
        sim.runOnce("Third run (independent RNG per call)", false, false);

        System.out.println("=== Field-size experiments ===");
        sim.runExperiment(new Field(5, 5), 30);
        sim.runExperiment(new Field(20, 20), 30);
    }

    public void runOnce(String label, boolean useShared, boolean resetShared) {
        System.out.println("--- " + label + " ---");
        Randomizer.setUseShared(useShared);
        if (useShared && resetShared) {
            Randomizer.reset();
        }

        List<Animal> animals = generator.createInitialPopulation();

        for (int day = 0; day < 5; day++) {
            simulateOneStep(animals);
        }

        for (Animal a : animals) {
            System.out.println(a.getName() + " age: " + a.getAge());
        }
        System.out.println();
    }

    public void simulateOneStep(List<Animal> animals) {
        for (Animal a : animals) {
            a.grow();
        }
    }

    public void runExperiment(Field field, int steps) {
        Randomizer.setUseShared(true);
        Randomizer.reset();
        List<Animal> pop = generator.createInitialPopulation(field);

        java.util.Random rng = Randomizer.getRandom();
        java.util.List<Integer> rabbitCounts = new java.util.ArrayList<>(steps);
        java.util.List<Integer> foxCounts = new java.util.ArrayList<>(steps);

        for (int day = 0; day < steps; day++) {
            int rabbits = 0, foxes = 0;
            for (Animal a : pop) {
                if (a instanceof Rabbit) rabbits++;
                else if (a instanceof Fox) foxes++;
            }
            rabbitCounts.add(rabbits);
            foxCounts.add(foxes);

            int capacity = field.getCapacity();
            java.util.List<Animal> next = new java.util.ArrayList<>(pop.size());

            for (Animal a : pop) {
                if (a instanceof Rabbit) {
                    next.add(a);
                    if (next.size() < capacity && rng.nextDouble() < 0.30) {
                        next.add(new Rabbit(0));
                    }
                    if (rng.nextDouble() < 0.05) {
                        next.remove(next.size() - 1);
                    }
                }
            }

            for (Animal a : pop) {
                if (a instanceof Fox) {
                    boolean ate = false;
                    if (!next.isEmpty()) {
                        for (int i = next.size() - 1; i >= 0; i--) {
                            if (next.get(i) instanceof Rabbit && rng.nextDouble() < 0.60) {
                                next.remove(i);
                                ate = true;
                                break;
                            }
                        }
                    }
                    if (rng.nextDouble() < (ate ? 0.05 : 0.20)) {
                        continue;
                    }
                    next.add(a);
                    if (ate && next.size() < capacity && rng.nextDouble() < 0.20) {
                        next.add(new Fox(0));
                    }
                }
            }
            pop = next;
        }
        System.out.printf("Field %dx%d, capacity=%d\n", field.getWidth(), field.getHeight(), field.getCapacity());
        graphView.plot(rabbitCounts, foxCounts);
    }
}
