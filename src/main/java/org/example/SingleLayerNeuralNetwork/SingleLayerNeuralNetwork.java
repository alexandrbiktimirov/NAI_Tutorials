package org.example.SingleLayerNeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class SingleLayerNeuralNetwork {
    private final List<Perceptron> neurons;
    private final double alpha;
    private final double beta;
    private final String[] classes = {"english", "french", "german"};

    public SingleLayerNeuralNetwork(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
        neurons = new ArrayList<>();

        for (int i = 0; i < classes.length; i++) {
            neurons.add(new Perceptron(26, 0.1));
        }
    }

    /**
     * trainLayer(inputs, labels) method trains each perceptron in the network using a local representation.
     * For each class the expected output is 1 if the input text belongs to that class and 0 otherwise
     * */
    public void trainLayer(double[][] inputs, String[] labels) {
        for (int n = 0; n < neurons.size(); n++) {
            int[] expected = new int[inputs.length];

            for (int i = 0; i < inputs.length; i++) {
                if (labels[i].equals(classes[n])) {
                    expected[i] = 1;
                } else {
                    expected[i] = 0;
                }
            }

            neurons.get(n).train(inputs, expected, alpha);
        }
    }

    /**
     * predict(input) method predicts the language of the input text by computing the net value for each perceptron
     * and returning the class corresponding to the highest net value (w^T * input - threshold)
     * */
    public String predict(double[] input) {
        double bestNet = Double.NEGATIVE_INFINITY;
        String predictedClass = "";

        for (int n = 0; n < neurons.size(); n++) {
            double net = 0.0;
            double[] weights = neurons.get(n).weights;

            for (int i = 0; i < weights.length; i++) {
                net += weights[i] * input[i];
            }

            net -= neurons.get(n).threshold;

            if (net > bestNet) {
                bestNet = net;
                predictedClass = classes[n];
            }
        }

        return predictedClass;
    }

    /**
     * textToVector(text) method converts input text to 26-dimensional vector to represent normalized frequency of each letter
     * */
    public static double[] textToVector(String text) {
        text = text.toLowerCase();
        text = text.replace('à', 'a');
        text = text.replace('â', 'a');
        text = text.replace('ä', 'a');
        text = text.replace('ç', 'c');
        text = text.replace('é', 'e');
        text = text.replace('è', 'e');
        text = text.replace('ê', 'e');
        text = text.replace('ë', 'e');
        text = text.replace('î', 'i');
        text = text.replace('ï', 'i');
        text = text.replace('ô', 'o');
        text = text.replace('ö', 'o');
        text = text.replace('ù', 'u');
        text = text.replace('û', 'u');
        text = text.replace('ü', 'u');
        text = text.replace('ß', 's');

        int[] counts = new int[26];
        int total = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                counts[ch - 'a']++;
                total++;
            }
        }
        double[] vector = new double[26];

        for (int i = 0; i < 26; i++) {
            vector[i] = (total > 0) ? ((double) counts[i] / total) : 0;
        }

        return vector;
    }
}