package org.example.BruteForce;

import java.util.*;

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
        } else if (type.equalsIgnoreCase("greedy density based approach")) {
            greedyDensityApproach();
        } else{
            while (true) {
                System.out.println("Enter the number of restarts");
                Scanner scanner = new Scanner(System.in);

                try{
                    int restarts = scanner.nextInt();
                    hillClimbing(restarts);

                    break;
                } catch(NumberFormatException e){
                    System.out.println("Enter an integer");
                }
            }
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
                if (objectiveFunction > bestObjectiveFunction) {
                    bestObjectiveFunction = objectiveFunction;
                    bestFeasibility = feasibility;
                    solutions.clear();
                    solutions.add(toBinaryArray(mask, n));
                } else if (objectiveFunction == bestObjectiveFunction) {
                    solutions.add(toBinaryArray(mask, n));
                }
            }
        }

        System.out.println("Brute‐force: best objective function = " + bestObjectiveFunction);
        System.out.println("Feasibility: " + bestFeasibility + " <= " + capacity);

        for (int[] vec : solutions) {
            printItems(n, vec);
        }
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

    public void hillClimbing(int numberOfRestarts) {
        int n = itemValues.length;
        Random rand = new Random();

        List<int[]> solutions = new ArrayList<>();

        int bestObjectiveFunction = 0;
        int bestFeasibility = 0;

        for (int restart = 1; restart <= numberOfRestarts; restart++) {
            int[] currentSolution = new int[n];
            int currentWeight = 0;
            int currentValue = 0;

            for (int i = 0; i < n; i++) {
                currentSolution[i] = rand.nextBoolean() ? 1 : 0;

                if (currentSolution[i] == 1) {
                    currentWeight += itemWeights[i];
                    currentValue += itemValues[i];
                }
            }

            while (currentWeight > capacity) {
                int i = rand.nextInt(n);

                if (currentSolution[i] == 1) {
                    currentSolution[i] = 0;
                    currentWeight -= itemWeights[i];
                    currentValue -= itemValues[i];
                }
            }

            boolean improvement = true;

            while (improvement) {
                improvement = false;

                int bestNeighborValue = currentValue;
                int bestNeighborWeight = currentWeight;
                int[] bestNeighbor = Arrays.copyOf(currentSolution, n);

                for (int i = 0; i < n; i++) {
                    int flippedWeight = currentWeight;
                    int flippedValue = currentValue;

                    if (currentSolution[i] == 1) {
                        flippedWeight -= itemWeights[i];
                        flippedValue -= itemValues[i];
                    } else {
                        flippedWeight += itemWeights[i];
                        flippedValue += itemValues[i];
                    }

                    if (flippedWeight <= capacity) {
                        if (flippedValue > bestNeighborValue) {
                            bestNeighborValue = flippedValue;
                            bestNeighborWeight = flippedWeight;
                            bestNeighbor = Arrays.copyOf(currentSolution, n);
                            bestNeighbor[i] = 1 - bestNeighbor[i];
                        }
                    }
                }

                if (bestNeighborValue > currentValue) {
                    currentSolution = bestNeighbor;
                    currentValue = bestNeighborValue;
                    currentWeight = bestNeighborWeight;
                    improvement = true;
                }
            }

            if (currentValue > bestObjectiveFunction) {
                bestObjectiveFunction = currentValue;
                bestFeasibility = currentWeight;
                solutions.clear();
                solutions.add(Arrays.copyOf(currentSolution, n));
            } else if (currentValue == bestObjectiveFunction) {
                boolean duplicate = false;

                for (int[] sol : solutions) {
                    if (Arrays.equals(sol, currentSolution)) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    solutions.add(Arrays.copyOf(currentSolution, n));
                }
            }
        }

        System.out.println("Hill-Climbing: best objective function = " + bestObjectiveFunction);
        System.out.println("Feasibility: " + bestFeasibility + " <= " + capacity);

        for (int[] sol : solutions) {
            printItems(n, sol);
        }
    }
}
