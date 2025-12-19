package task4_5;

import java.util.Random;

public final class Randomizer {
    private static final long SEED = 42L; // fixed seed for repeatable sequences
    private static boolean useShared = true;
    private static Random sharedRandom = new Random(SEED);

    private Randomizer() {
    }

    public static Random getRandom() {
        return useShared ? sharedRandom : new Random();
    }

    public static void setUseShared(boolean useSharedValue) {
        useShared = useSharedValue;
        if (useShared) {
            reset();
        }
    }

    public static void reset() {
        sharedRandom = new Random(SEED);
    }
}
