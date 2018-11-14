package de.rosenau.simon.qlearning;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Project created by Simon Rosenau.
 */

public class QLearning {

    // Configuration

    private int maxEposide = 5000000;
    private double learningRate = 0.1;
    private double discountRate = 0.9;

    private double epsilon = 1;
    private double minEpsilon = 0.1;
    private double decayRate = 0.1;

    // End Configuration

    private double[][] qtable = new double[10][2];
    private int episode = 0;
    private int state = 0;
    private int step = 1;

    public void learn() {
        long start = System.currentTimeMillis();
        while(episode < maxEposide) {
            episode++;

            while(!isSuccess(state)) {
                int action;
                if (Math.random() < epsilon) {
                    action = new Random().nextInt(2);
                } else {
                    action = Utils.max(qtable[state]);
                }

                int newState = performAction(state, action);

                double reward = (-0.1 * step) + (newState == 9 ? 20 : 0);

                qtable[state][action] = qtable[state][action] + learningRate * (reward + discountRate * qtable[newState][Utils.max(qtable[newState])] - qtable[state][action]);
                state = newState;
                step++;
            }

            step = 0;
            state = 0;
            epsilon = minEpsilon + (1 - minEpsilon) * Math.pow(Math.E, -decayRate * episode);
            //System.out.println(episode);
        }
        System.out.println();
        printTable(qtable);
        System.out.println("Learned " + episode + " episodes in " + (System.currentTimeMillis() - start) + "ms");
        System.out.println();
    }

    public void run() {
        printState(state);
        while(!isSuccess(state)) {
            int action = Utils.max(qtable[state]);
            state = performAction(state, action);
            printState(state);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        state = 0;
    }

    public boolean isSuccess(int state) {
        return state == 9;
    }

    public int performAction(int state, int action) {
        int a = state + (action * 2 - 1);
        return a < 0 ? 0 : a > 9 ? 9 : a;
    }

    public void printState(int state) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < state; i++) {
            builder.append("-");
        }
        builder.append("0");
        for (int i = state + 1; i < 10; i++) {
            builder.append("-");
        }
        System.out.println(builder.toString());
    }

    public void printTable(double[][] table) {
        for (int i = 0; i < table.length; i++) {
            double[] row = table[i];

            System.out.print(i + ".");
            for (double column : row) {
                System.out.print(" | " + column);
            }
            System.out.print("\n");
        }
    }

}
