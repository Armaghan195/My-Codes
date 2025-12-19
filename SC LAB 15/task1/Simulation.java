package task1;

public class Simulation {
    public static void main(String[] args) {
        runOnce("First run (shared, seeded)");

        Randomizer.reset();
        runOnce("Second run after reset (identical to first)");

        Randomizer.setUseShared(false);
        runOnce("Third run (independent RNG per call)");
    }

    private static void runOnce(String label) {
        System.out.println("--- " + label + " ---");
        Animal fox = new Animal("Fox", 1);
        Animal rabbit = new Animal("Rabbit", 0);

        for (int day = 0; day < 5; day++) {
            fox.grow();
            rabbit.grow();
        }

        System.out.println(fox.getName() + " age: " + fox.getAge());
        System.out.println(rabbit.getName() + " age: " + rabbit.getAge());
        System.out.println();
    }
}