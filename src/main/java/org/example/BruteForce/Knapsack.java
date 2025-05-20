package org.example.BruteForce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Knapsack {
    private int[] itemWeights;
    private int[] itemValues;
    private final int capacity;

    public Knapsack(int[] itemWeights, int[] itemValues, int capacity, String type) {
        this.itemWeights = itemWeights;
        this.itemValues = itemValues;
        this.capacity = capacity;

        long startTime = System.nanoTime();
        long endTime;
        System.out.println("-------------------------------------------------------");
        if (type.equalsIgnoreCase("brute force")) {
            bruteForce();
            endTime = System.nanoTime();
        } else {
            greedyDensityApproach();
            endTime = System.nanoTime();
        }
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

    public int[][] bruteForce() {
        int n = itemValues.length;
        int totalSubsets = 1 << n;
        int bestValue = 0;
        int bestFeasibility = 0;

        List<int[]> solutions = new ArrayList<>();

        for (int mask = 0; mask < totalSubsets; mask++) {
            int feasibility = 0;
            int objectiveFunction = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    feasibility += itemWeights[i];
                    objectiveFunction += itemValues[i];
                }
            }

            if (feasibility <= capacity) {
                if (objectiveFunction > bestValue) {
                    bestValue = objectiveFunction;
                    bestFeasibility = feasibility;
                    solutions.clear();
                    solutions.add(toBinaryArray(mask, n));
                } else if (objectiveFunction == bestValue) {
                    solutions.add(toBinaryArray(mask, n));
                }
            }
        }

        System.out.println("Brute‐force: best total value = " + bestValue);
        System.out.println("Feasibility: " + bestFeasibility);
        System.out.println("Optimal selections (as 0/1 vectors):");
        for (int[] vec : solutions) {
            System.out.println(Arrays.toString(vec));
        }



        int[][] result = new int[solutions.size()][n];

        for (int i = 0; i < solutions.size(); i++) {
            result[i] = solutions.get(i);
        }

        return result;
    }

    private int[] toBinaryArray(int mask, int n) {
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = ((mask & (1 << i)) != 0) ? 1 : 0;
        }

        return arr;
    }


    public int[][] greedyDensityApproach() {
        int n = itemValues.length;

        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }

        Arrays.sort(idx, Comparator.comparingDouble(
                i -> -((double) itemValues[i] / itemWeights[i])
        ));

        int remaining = capacity;
        int feasibility = 0, objectiveFunction = 0;
        int[] selection = new int[n];

        for (int i : idx) {
            if (itemWeights[i] <= remaining) {
                remaining -= itemWeights[i];
                feasibility += itemWeights[i];
                objectiveFunction += itemValues[i];
                selection[i] = 1;
            }
        }

        System.out.println("Greedy-density: total value = " + objectiveFunction);
        System.out.println("Feasibility = " + feasibility);
        System.out.println("Selection vector: " + Arrays.toString(selection));

        System.out.print("Selected item weights: ");
        for (int i = 0; i < n; i++) {
            if (selection[i] == 1) System.out.print(itemWeights[i] + " ");
        }
        System.out.print("\nSelected item values:  ");
        for (int i = 0; i < n; i++) {
            if (selection[i] == 1) System.out.print(itemValues[i] + " ");
        }
        System.out.println();

        return new int[][]{selection};
    }
}
