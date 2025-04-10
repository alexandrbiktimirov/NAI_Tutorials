package org.example.KNN;

import java.util.List;

public class EvaluationMetrics {
    public static double measureAccuracy(List<String> realClasses, List<String> predictedClasses) {
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
}
