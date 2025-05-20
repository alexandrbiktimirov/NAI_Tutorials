package org.example.BruteForce;

import java.util.Arrays;
import java.util.Comparator;

public class Knapsack {
    private final int[] itemWeights;
    private final int[] itemValues;
    private final int capacity;

    public Knapsack(int[] itemWeights, int[] itemValues, int capacity, String type) {
        this.itemWeights = itemWeights;
        this.itemValues = itemValues;
        this.capacity = capacity;

        System.out.println("-------------------------------------------------------");

        long startTime = System.nanoTime();

        if (type.equalsIgnoreCase("brute force")) {
            bruteForce();
        } else {
            greedyDensityApproach();
        }

        long endTime = System.nanoTime();
        double duration = endTime - startTime;

        System.out.println("Time taken: " + duration / 1_000_000_000 + "s");
        System.out.println("-------------------------------------------------------\n");
    }

    public Knapsack(int[] itemWeights, int[] itemValues, int capacity) {
        this.itemWeights = itemWeights;
        this.itemValues = itemValues;
        this.capacity = capacity;

        System.out.println("-------------------------------------------------------");

        long startTime = System.nanoTime();
        bruteForce();
        long endTime = System.nanoTime();
        double duration = endTime - startTime;
        System.out.println("Time taken for Brute Force: " + duration / 1_000_000_000 + "s");

        System.out.println("-------------------------------------------------------");

        startTime = System.nanoTime();
        greedyDensityApproach();
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Time taken for Greedy Density: " + duration / 1_000_000_000 + "s");

        System.out.println("-------------------------------------------------------\n");
    }

    public void bruteForce() {
        int n = itemValues.length;
        int totalSubsets = 1 << n;
        int bestObjectiveFunction = 0;
        int bestFeasibility = 0;
        int[] bestSolution = new int[n];

        for (int mask = 0; mask < totalSubsets; mask++) {
            int feasibility = 0;
            int objectiveFunction = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    feasibility += itemWeights[i];
                    objectiveFunction += itemValues[i];
                }
            }

            if (feasibility <= capacity && objectiveFunction > bestObjectiveFunction) {
                bestObjectiveFunction = objectiveFunction;
                bestFeasibility = feasibility;
                bestSolution = toBinaryArray(mask, n);
            }
        }

        System.out.println("Brute‐force: best objective function = " + bestObjectiveFunction);
        System.out.println("Feasibility: " + bestFeasibility + " <= " + capacity);

        printItems(n, bestSolution);
    }

    private void printItems(int n, int[] vec) {
        System.out.print("Selected item weights: ");
        for (int i = 0; i < n; i++) {
            if (vec[i] == 1) System.out.print(itemWeights[i] + " ");
        }
        System.out.print("\nSelected item values:  ");
        for (int i = 0; i < n; i++) {
            if (vec[i] == 1) System.out.print(itemValues[i] + " ");
        }
        System.out.println();
    }

    private int[] toBinaryArray(int mask, int n) {
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = ((mask & (1 << i)) != 0) ? 1 : 0;
        }

        return arr;
    }


    public void greedyDensityApproach() {
        int n = itemValues.length;

        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, Comparator.comparingDouble(
            i -> -((double) itemValues[i] / itemWeights[i])
        ));

        int remainingWeight = capacity;
        int feasibility = 0;
        int objectiveFunction = 0;
        int[] binaryArray = new int[n];

        for (int i : indices) {
            if (itemWeights[i] <= remainingWeight) {
                remainingWeight -= itemWeights[i];
                feasibility += itemWeights[i];
                objectiveFunction += itemValues[i];
                binaryArray[i] = 1;
            }
        }

        System.out.println("Greedy-density: best objective function = " + objectiveFunction);
        System.out.println("Feasibility: " + feasibility + " <= " + capacity);

        printItems(n, binaryArray);
    }
}
