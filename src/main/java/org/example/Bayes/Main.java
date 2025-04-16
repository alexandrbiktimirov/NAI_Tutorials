package org.example.Bayes;

public class Main {

    public static void main(String[] args) {
        System.out.println(smooth(0, 4, 5));
        System.out.println(smooth(0, 6, 7));
    }

    public static double smooth(int numerator, int denominator, int numberOfAttributes){
        return (double) (numerator + 1) / (denominator + numberOfAttributes);
    }
}
