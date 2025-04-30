package org.example.KMeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static int findClosestCluster(double[] vector, double[][] centroids) {
        double[] distances = new double[centroids.length];

        double minimalDistance = Double.POSITIVE_INFINITY;
        List<Integer> minimalIndices = new ArrayList<>();

        for (int i = 0; i < centroids.length; i++) {
            distances[i] = calculateEuclideanDistance(vector, centroids[i]);

            if (distances[i] < minimalDistance) {
                minimalDistance = distances[i];
                minimalIndices.clear();
                minimalIndices.add(i);
            } else if (distances[i] == minimalDistance) {
                minimalIndices.add(i);
            }
        }

        Random random = new Random();
        return minimalIndices.get(random.nextInt(minimalIndices.size()));
    }


    public static double calculateEuclideanDistance(double[] a, double[] b) {
        double sum = 0;

        for (int i = 0; i < a.length; i++) {
            double difference = a[i] - b[i];
            sum += Math.pow(difference, 2);
        }

        return Math.sqrt(sum);
    }

    public static void main(String[] args) {
        double[][] centroids = {
                {0.0, 0.0},
                {5.0, 5.0},
                {5.0, 5.0},
                {10.0, 0.0}
        };

        double[] a = {6.0, 4.5};



        System.out.println(findClosestCluster(a, centroids));
    }
}
