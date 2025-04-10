package org.example.SingleLayerNeuralNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<double[]> data = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        String[] languages = {"english", "french", "german"};

        for (String language : languages) {
            File folder = new File("src/main/resources/" + language);
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    String content = readFile(file.getPath());

                    for (String line : content.split("\n")) {
                        double[] vec = SingleLayerNeuralNetwork.textToVector(line);
                        data.add(vec);
                        labels.add(language);
                    }
                }
            } else {
                System.out.println("Files not found");
                return;
            }
        }

        int total = data.size();
        Random rand = new Random();

        //Randomizing order of the dataset (shuffling array)
        for (int i = total - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            double[] temp = data.get(i);
            data.set(i, data.get(j));
            data.set(j, temp);
            String tempLabel = labels.get(i);
            labels.set(i, labels.get(j));
            labels.set(j, tempLabel);
        }

        int trainSize = (int) (total * 0.7);
        double[][] trainData = new double[trainSize][];
        String[] trainLabels = new String[trainSize];
        double[][] testData = new double[total - trainSize][];
        String[] testLabels = new String[total - trainSize];

        for (int i = 0; i < trainSize; i++) {
            trainData[i] = data.get(i);
            trainLabels[i] = labels.get(i);
        }

        for (int i = trainSize; i < total; i++) {
            testData[i - trainSize] = data.get(i);
            testLabels[i - trainSize] = labels.get(i);
        }

        //Creating and training single-layer neural network
        SingleLayerNeuralNetwork network = new SingleLayerNeuralNetwork(0.1, 0.0);
        network.trainLayer(trainData, trainLabels);

        //Building a confusion matrix for evaluating accuracy
        //Rows - actual labels
        //Columns - predicted labels
        int[][] confusionMatrix = new int[3][3];
        List<Integer> realClasses = new ArrayList<>();
        List<Integer> predictedClasses = new ArrayList<>();

        //Predicting the test samples and updating the confusion matrix
        for (int i = 0; i < testData.length; i++) {
            String predicted = network.predict(testData[i]);
            int predIdx = getIndex(predicted, languages);
            int trueIdx = getIndex(testLabels[i], languages);

            predictedClasses.add(predIdx);
            realClasses.add(trueIdx);
            confusionMatrix[trueIdx][predIdx]++;
        }

        //Calculating accuracy
        double accuracy = EvaluationMetrics.measureAccuracy(realClasses, predictedClasses);
        System.out.println("Overall Accuracy: " + (accuracy * 100) + "%");

        //Calculating and displaying precision, recall, and f-measure for each language
        for (int i = 0; i < 3; i++) {
            double precision = EvaluationMetrics.precision(confusionMatrix, i);
            double recall = EvaluationMetrics.recall(confusionMatrix, i);
            double fMeasure = EvaluationMetrics.fMeasure(confusionMatrix, i);
            System.out.println("\n-------------------------------");
            System.out.println("Class " + languages[i] + " Precision: " + precision);
            System.out.println("Class " + languages[i] + " Recall: " + recall);
            System.out.println("Class " + languages[i] + " F-Measure: " + fMeasure);
        }

        //Getting user input to classify the text
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nEnter text to classify:");
            String inputText = scanner.nextLine();

            if (inputText.equals("exit")) {
                System.exit(0);
            }

            double[] inputVector = SingleLayerNeuralNetwork.textToVector(inputText);
            String result = network.predict(inputVector);
            System.out.println("Predicted language: " + result);
        }
    }
    /**
     * getIndex(String label, String[] languages) returns the index of the given label in the languages array
    * */
    public static int getIndex(String label, String[] languages) {
        for (int i = 0; i < languages.length; i++) {
            if (languages[i].equals(label)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * readFile(String filePath) reads the content of a file given its path and returns it as a single string
     * */
    public static String readFile(String filePath) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}