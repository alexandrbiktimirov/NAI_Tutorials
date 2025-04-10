package org.example.Perceptron;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        List<Vector> dataset = PrepareDataset.loadIrisDataset("src/main/resources/iris.csv");

        //Splitting the dataset
        Vector[][] split = PrepareDataset.trainTestSplit(dataset);
        Vector[] trainSet = split[0];
        Vector[] testSet = split[1];

        double[][] trainInputs = new double[trainSet.length][4];

        int[] trainLabels = new int[trainSet.length];
        for(int i = 0; i < trainSet.length; i++){
            for(int j = 0; j < 4; j++){
                trainInputs[i][j] = trainSet[i].features[j];
            }

            trainLabels[i] = Integer.parseInt(trainSet[i].label);
        }

        double[][] testInputs = new double[testSet.length][4];

        int[] testLabels = new int[testSet.length];
        for(int i = 0; i < testSet.length; i++){
            for(int j = 0; j < 4; j++){
                testInputs[i][j] = testSet[i].features[j];
            }

            testLabels[i] = Integer.parseInt(testSet[i].label);
        }

        //Creating and training perceptron
        Perceptron perceptron = new Perceptron(4, 0);
        perceptron.train(trainInputs, trainLabels, 0.1);

        //Evaluating trained perceptron on the test dataset
        List<Integer> predictions = new ArrayList<>();
        for (double[] testInput : testInputs) {
            predictions.add(perceptron.predict(testInput));
        }
        List<Integer> actual = new ArrayList<>();
        for (int testLabel : testLabels) {
            actual.add(testLabel);
        }

        double accuracy = EvaluationMetrics.measureAccuracy(actual, predictions);
        System.out.println("Test Accuracy: " + accuracy * 100 + "%");

        //Computing means of sepal_length and sepal_width for plotting the graph
        double sum0 = 0;
        double sum1 = 0;
        int count = 0;
        for (Vector vector : trainSet) {
            sum0 += vector.features[0];
            sum1 += vector.features[1];
            count++;
        }

        //Plotting a graph
        double mean0 = sum0 / count;
        double mean1 = sum1 / count;
        new PlotFrame("Perceptron Decision Boundary", testSet, perceptron, mean0, mean1);

        //Input for user
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 4 features separated by space:");
        String inputLine = sc.nextLine();
        String[] parts = inputLine.split(" ");
        double[] newFeatures = new double[4];

        for(int i = 0; i < 4; i++){
            newFeatures[i] = Double.parseDouble(parts[i]);
        }

        int newPrediction = perceptron.predict(newFeatures);
        String predictedLabel = (newPrediction == 1) ? "setosa" : "versicolor";
        System.out.println("Predicted class: " + predictedLabel);
    }
}
