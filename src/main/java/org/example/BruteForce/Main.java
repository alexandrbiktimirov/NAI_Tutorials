package org.example.BruteForce;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int capacity = 10;
        int[] weight = {8, 10 ,3, 5, 2};
        int[] values = {10, 12, 5, 6, 2};

        boolean running = true;

        while (running){
            System.out.println("Choose one of the following options (1/2/3) or 'q' to exit:");
            System.out.println("1. Brute Force method");
            System.out.println("2. Greedy density method");
            System.out.println("3. Both methods");
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
                default -> System.out.println("Incorrect input, please try again");
            }
        }
    }
}
