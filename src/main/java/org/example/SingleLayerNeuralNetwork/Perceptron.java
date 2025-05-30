package org.example.SingleLayerNeuralNetwork;

public class Perceptron {
    public int dimension;
    public double[] weights;
    public double threshold;
    public double alpha;
    public double beta;

    public Perceptron(int inputSize, double threshold) {
        this.dimension = inputSize;
        this.threshold = threshold;
        weights = new double[inputSize];

        for(int i = 0; i < inputSize; i++){
            weights[i] = Math.random();
        }

        alpha = 0;
        beta = 0;
    }

    public int predict(double[] inputs) {
        double sum = 0.0;

        for (int i = 0; i < inputs.length; i++) {
            sum += weights[i] * inputs[i];
        }

        return (sum >= threshold) ? 1 : 0;
    }

    public void train(double[][] inputs, int[] correctOutput, double learningRate) {
        int epochs = 0;
        boolean errorFlag;
        int maximumEpochs = 200;

        do {
            errorFlag = false;
            epochs++;

            for (int i = 0; i < inputs.length; i++) {
                int prediction = predict(inputs[i]);
                int error = correctOutput[i] - prediction; //(d-y)

                if (error != 0) {
                    errorFlag = true;

                    for (int j = 0; j < inputs[i].length; j++) {
                        weights[j] += learningRate * error * inputs[i][j];//W'
                    }

                    threshold -= learningRate * error;//O'
                }
            }
        } while (errorFlag && epochs < maximumEpochs);

        if (epochs >= maximumEpochs) {
            System.out.println("Exceeded maximum number of epochs");
        }
    }
}