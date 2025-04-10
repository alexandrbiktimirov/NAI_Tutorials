package org.example.KNN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename = "src/main/resources/iris.csv";
        List<Vector> dataset = PrepareDataset.loadIrisDataset(filename);

        if (dataset.isEmpty()) {
            System.out.println("Dataset is empty or could not be loaded.");
            return;
        }

        Vector[][] split = PrepareDataset.trainTestSplit(dataset);
        List<Vector> trainDataset = Arrays.asList(split[0]);
        List<Vector> testDataset = Arrays.asList(split[1]);
        System.out.println("Training set size: " + trainDataset.size());
        System.out.println("Test set size: " + testDataset.size());

        int[] kValues = {1, 2, 4, 5, 7, 9};
        for (int k : kValues) {
            KNearestNeighbours knn = new KNearestNeighbours(k, trainDataset);
            List<String> predictedClasses = new ArrayList<>();
            List<String> realClasses = new ArrayList<>();

            for (Vector obs : testDataset) {
                String predicted = knn.predict(obs.features);
                predictedClasses.add(predicted);
                realClasses.add(obs.label);
            }

            double proportion = EvaluationMetrics.measureAccuracy(realClasses, predictedClasses);
            double percentage = proportion * 100.0;
            System.out.println("Accuracy for k = " + k + ": " + String.format("%.2f%%", percentage));
        }

        Scanner scanner = new Scanner(System.in);
        KNearestNeighbours knn = new KNearestNeighbours(5, trainDataset);
        while (true) {
            System.out.println("Enter new observation features separated by commas (sepal_length,sepal_width,petal_length,petal_width) or 'q' to quit:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                break;
            }

            String[] tokens = input.split(",");

            if (tokens.length != 4) {
                System.out.println("Invalid input. Please enter exactly 4 feature values.");
                continue;
            }

            try {
                double[] features = new double[4];

                for (int i = 0; i < 4; i++) {
                    features[i] = Double.parseDouble(tokens[i].trim());
                }

                String predicted = knn.predict(features);
                System.out.println("Predicted class: " + predicted);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + e.getMessage());
            }
        }

        scanner.close();
    }
}