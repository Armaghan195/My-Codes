package task8_9;

/**
 * Actor interface represents any entity that can act in the simulation.
 * All actors must be able to age and participate in the simulation.
 */
public interface Actor {
    /**
     * Advance the actor by one simulation step (grow/age).
     */
    void grow();

    /**
     * Get the name of this actor.
     * @return The actor's name.
     */
    String getName();

    /**
     * Get the current age of this actor.
     * @return The actor's age.
     */
    int getAge();
}
