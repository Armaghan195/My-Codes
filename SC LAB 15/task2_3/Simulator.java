package task2_3;

import java.util.List;

public class Simulator {
    private final PopulationGenerator generator;
    private final GraphView graphView = new GraphView(40);

    public Simulator(PopulationGenerator generator) {
        this.generator = generator;
    }

    public static void main(String[] args) {
        Simulator sim = new Simulator(new PopulationGenerator());
        // Keep original simple runs
        sim.runOnce("First run (shared, seeded)", true, true);
        sim.runOnce("Second run after reset (identical to first)", true, true);
        sim.runOnce("Third run (independent RNG per call)", false, false);

        // New experiments: different field sizes with graph view
        System.out.println("=== Field-size experiments ===");
        sim.runExperiment(new Field(5, 5), 30);    // small field (25 cells)
        sim.runExperiment(new Field(20, 20), 30);  // larger field (400 cells)
    }

    /**
     * Run a single simulation label with configurable RNG behavior.
     * @param label description to print
     * @param useShared whether to use shared RNG
     * @param resetShared whether to reset shared RNG before running
     */
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

    /**
     * Advance the simulation by one step for all animals.
     * This method is decoupled from concrete animal classes.
     */
    public void simulateOneStep(List<Animal> animals) {
        for (Animal a : animals) {
            a.grow();
        }
    }

    /**
     * Run predator-prey style dynamics with capacity limits and show a simple graph.
     */
    public void runExperiment(Field field, int steps) {
        Randomizer.setUseShared(true);
        Randomizer.reset();
        List<Animal> pop = generator.createInitialPopulation(field);

        java.util.Random rng = Randomizer.getRandom();
        java.util.List<Integer> rabbitCounts = new java.util.ArrayList<>(steps);
        java.util.List<Integer> foxCounts = new java.util.ArrayList<>(steps);

        for (int day = 0; day < steps; day++) {
            // Count species
            int rabbits = 0, foxes = 0;
            for (Animal a : pop) {
                if (a instanceof Rabbit) rabbits++;
                else if (a instanceof Fox) foxes++;
            }

            rabbitCounts.add(rabbits);
            foxCounts.add(foxes);

            // Reproduction and mortality
            int capacity = field.getCapacity();
            java.util.List<Animal> next = new java.util.ArrayList<>(pop.size());

            // Rabbits: chance to breed, small chance to die
            for (Animal a : pop) {
                if (a instanceof Rabbit) {
                    next.add(a);
                    if (next.size() < capacity && rng.nextDouble() < 0.30) {
                        next.add(new Rabbit(0));
                    }
                    if (rng.nextDouble() < 0.05) {
                        // rabbit dies: remove last added rabbit if possible
                        next.remove(next.size() - 1);
                    }
                }
            }

            // Foxes: hunt rabbits if available; may starve; may breed after hunt
            for (Animal a : pop) {
                if (a instanceof Fox) {
                    boolean ate = false;
                    if (!next.isEmpty()) {
                        // attempt to find a rabbit to remove
                        for (int i = next.size() - 1; i >= 0; i--) {
                            if (next.get(i) instanceof Rabbit && rng.nextDouble() < 0.60) {
                                next.remove(i);
                                ate = true;
                                break;
                            }
                        }
                    }

                    // Starvation
                    if (rng.nextDouble() < (ate ? 0.05 : 0.20)) {
                        // fox dies, skip adding
                        continue;
                    }

                    // Surviving fox remains
                    next.add(a);

                    // Breed after successful hunt
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
