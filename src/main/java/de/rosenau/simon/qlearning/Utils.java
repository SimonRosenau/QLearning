package de.rosenau.simon.qlearning;

/**
 * Project created by Simon Rosenau.
 */

public class Utils {

    public static int max(double[] array) {
        int max = 0;
        double old = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > old) {
                max = i;
                old = array[i];
            }
        }
        return max;
    }

}
