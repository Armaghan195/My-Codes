
package task4_5;

import java.util.List;

public class GraphView {
    private final int maxBarWidth;

    public GraphView(int maxBarWidth) {
        this.maxBarWidth = Math.max(10, maxBarWidth);
    }

    public void plot(List<Integer> rabbits, List<Integer> foxes) {
        int steps = Math.min(rabbits.size(), foxes.size());
        int max = 1;
        for (int i = 0; i < steps; i++) {
            max = Math.max(max, Math.max(rabbits.get(i), foxes.get(i)));
        }
        System.out.println("\nPopulation Graph (R=rabbits, F=foxes):");
        for (int i = 0; i < steps; i++) {
            int r = rabbits.get(i);
            int f = foxes.get(i);
            int rLen = scale(r, max);
            int fLen = scale(f, max);
            String rBar = repeat('R', rLen);
            String fBar = repeat('F', fLen);
            System.out.printf("%2d | R:%-" + maxBarWidth + "s (%d)  F:%-" + maxBarWidth + "s (%d)%n", i + 1, rBar, r, fBar, f);
        }
        System.out.println();
    }

    private int scale(int value, int max) {
        return Math.max(0, (int) Math.round((value / (double) max) * maxBarWidth));
    }

    private String repeat(char c, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) sb.append(c);
        return sb.toString();
    }
}
