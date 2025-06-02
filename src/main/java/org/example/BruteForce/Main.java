package org.example.BruteForce;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int capacity = 11;
        int[] weight = {8, 7, 8, 3, 9, 9, 11, 9, 2, 5, 3, 3, 2, 4, 10, 2, 11, 10, 9, 8, 4, 11, 6, 10, 7, 7, 10, 3};
        int[] values = {49, 82, 66, 9, 28, 45, 95, 36, 37, 75, 70, 54, 31, 38, 72, 18, 2, 2, 65, 64, 7, 21, 26, 94, 21, 46, 32, 33};

        boolean running = true;

        while (running){
            System.out.println("Choose one of the following options (1/2/3) or 'q' to exit:");
            System.out.println("1. Brute Force method");
            System.out.println("2. Greedy density method");
            System.out.println("3. Both methods");
            System.out.println("4. Hill climbing method");
            Scanner input = new Scanner(System.in);
            String option = input.nextLine();

            if (option.equalsIgnoreCase("q")){
                running = false;
                continue;
            }

            int optionInt;
            try{
                optionInt = Integer.parseInt(option);
            } catch(Exception e){
                System.out.println("Incorrect input, please try again");
                continue;
            }

            Knapsack knapsack;
            switch (optionInt){
                case 1 -> knapsack = new Knapsack(weight, values, capacity, "Brute Force");
                case 2 -> knapsack = new Knapsack(weight, values, capacity, "Greedy Density Based Approach");
                case 3 -> knapsack = new Knapsack(weight, values, capacity);
                case 4 -> knapsack = new Knapsack(weight, values, capacity, "Hill Climbing Approach");
                default -> System.out.println("Incorrect input, please try again");
            }
        }
    }
}
