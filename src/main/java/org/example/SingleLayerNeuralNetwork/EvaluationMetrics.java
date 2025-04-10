package org.example.SingleLayerNeuralNetwork;

import java.util.List;

public class EvaluationMetrics {
    public static double measureAccuracy(List<Integer> realClasses, List<Integer> predictedClasses) {
        if (realClasses.size() != predictedClasses.size() || realClasses.isEmpty()) {
            return 0.0;
        }

        int correct = 0;

        for (int i = 0; i < realClasses.size(); i++) {
            if (realClasses.get(i).equals(predictedClasses.get(i))) {
                correct++;
            }
        }

        return (double) correct / realClasses.size();
    }

    public static double precision(int[][] matrix, int positive) {
        int truePositives = matrix[positive][positive];
        int falsePositives = 0;

        for (int i = 0; i < matrix.length; i++)
            if (i != positive)
                falsePositives += matrix[i][positive];

        if ((truePositives + falsePositives) == 0){
            return 0;
        }

        return truePositives / (double)(truePositives + falsePositives);
    }

    public static double recall(int[][] matrix, int positive) {
        int truePositives = matrix[positive][positive];
        int falseNegatives = 0;

        for (int i = 0; i < matrix[positive].length; i++)
            if (i != positive)
                falseNegatives += matrix[positive][i];

        if ((truePositives + falseNegatives) == 0){
            return 0;
        }

        return truePositives / (double)(truePositives + falseNegatives);
    }

    public static double fMeasure(int[][] matrix, int positive) {
        double precision = precision(matrix, positive);
        double recall = recall(matrix, positive);

        if (precision + recall == 0)
            return 0;
        return 2 * precision * recall / (precision + recall);
    }

}
