package de.rosenau.simon.qlearning;

/**
 * Project created by Simon Rosenau.
 */

public class Main {

    public static void main(String[] args) {
        QLearning learning = new QLearning();
        learning.learn();
        learning.run();
    }

}
