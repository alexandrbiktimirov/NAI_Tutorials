package org.example.Bayes;

import org.example.SingleLayerNeuralNetwork.EvaluationMetrics;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String[]> dataset = PrepareDataset.prepareDataset("src/main/resources/outGame.csv");

        if (dataset == null) {
            return;
        }

        Collections.shuffle(dataset);
        List<String[]> testDataset = new ArrayList<>(dataset.subList(0, 2));
        List<String[]> trainDataset = new ArrayList<>(dataset.subList(2, dataset.size()));

        NaiveBayesClassifier classifier = new NaiveBayesClassifier(false, trainDataset);

        Set<String> classSet = new HashSet<>();

        for (String[] row : trainDataset) {
            classSet.add(row[row.length - 1]);
        }

        List<String> classes = new ArrayList<>(classSet);

        Map<String, Integer> classIndex = new HashMap<>();

        for (int i = 0; i < classes.size(); i++) {
            classIndex.put(classes.get(i), i);
        }

        int n = classes.size();
        int[][] confusionMatrix = new int[n][n];
        List<Integer> realList = new ArrayList<>();
        List<Integer> predictedList = new ArrayList<>();

        for (String[] row : testDataset) {
            String actual = row[row.length - 1];
            String pred = classifier.predict(row);

            int actualI = classIndex.get(actual);
            int predictedI = classIndex.get(pred);

            confusionMatrix[actualI][predictedI]++;

            realList.add(actualI);
            predictedList.add(predictedI);
        }

        double accuracy = EvaluationMetrics.measureAccuracy(realList, predictedList);
        System.out.println("Accuracy: " +  accuracy * 100 + "%");

        for (int i = 0; i < n; i++) {
            double precision = EvaluationMetrics.precision(confusionMatrix, i);
            double recall = EvaluationMetrics.recall(confusionMatrix, i);
            double fMeasure = EvaluationMetrics.fMeasure(confusionMatrix, i);

            System.out.println("Class: " + classes.get(i));
            System.out.printf("Precision: %.2f%n", precision);
            System.out.printf("Recall: %.2f%n", recall);
            System.out.printf("F-Measure: %.2f%n", fMeasure);
        }
    }
}