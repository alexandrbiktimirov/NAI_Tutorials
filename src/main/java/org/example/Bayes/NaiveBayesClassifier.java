package org.example.Bayes;

import java.util.*;

public class NaiveBayesClassifier {
    private final boolean applySmoothingAll;
    private final List<String[]> trainDataset;

    private final Map<String, Double> classPriors        = new HashMap<>();
    private final Map<String, List<Map<String, Double>>> classPosteriors = new HashMap<>();

    private final Map<String, Integer> classCounts        = new HashMap<>();
    private final Map<Integer, Integer> attributeValueSizes = new HashMap<>();

    public NaiveBayesClassifier(boolean applySmoothingAll, List<String[]> trainDataset) {
        this.applySmoothingAll = applySmoothingAll;
        this.trainDataset = trainDataset;
        train();
    }

    private void train() {
        int total = trainDataset.size();
        Map<Integer, Set<String>> attributeValues = new HashMap<>();
        Map<String, Map<Integer, Map<String, Integer>>> conditionalCounts = new HashMap<>();

        for (String[] row : trainDataset) {
            String clazz = row[row.length - 1];
            classCounts.put(clazz, classCounts.getOrDefault(clazz, 0) + 1);
            conditionalCounts.putIfAbsent(clazz, new HashMap<>());

            for (int i = 0; i < row.length - 1; i++) {
                attributeValues.computeIfAbsent(i, _ -> new HashSet<>()).add(row[i]);

                var byAttr = conditionalCounts.get(clazz);
                byAttr.computeIfAbsent(i, _ -> new HashMap<>())
                        .merge(row[i], 1, Integer::sum);
            }
        }

        // "a priori" probabilities: P(class)
        for (var e : classCounts.entrySet()) {
            classPriors.put(e.getKey(), (double)e.getValue() / total);
        }

        // "a posteriori" probabilities: P(attribute=value | class)
        for (String clazz : classCounts.keySet()) {
            int classCount = classCounts.get(clazz);
            List<Map<String, Double>> attributeList = new ArrayList<>();

            for (int i = 0; i < attributeValues.size(); i++) {
                Set<String> values = attributeValues.get(i);
                int numberOfValues = values.size();
                attributeValueSizes.put(i, numberOfValues);

                Map<String, Integer> counts = conditionalCounts.get(clazz).getOrDefault(i, Map.of());
                Map<String, Double> valueProbability = new HashMap<>();

                for (String value : values) {
                    int count = counts.getOrDefault(value, 0);
                    double probability;

                    if (applySmoothingAll || count == 0) {
                        probability = smooth(count, classCount, numberOfValues);
                    } else {
                        probability = (double)count / classCount;
                    }

                    valueProbability.put(value, probability);
                }
                attributeList.add(valueProbability);
            }

            classPosteriors.put(clazz, attributeList);
        }
    }

    public double smooth(int numerator, int denominator, int numberOfValues) {
        return (double)(numerator + 1) / (denominator + numberOfValues);
    }

    public String predict(String[] input) {
        int m = input.length - 1;
        String bestClass = null;
        double bestProbability = -1.0;

        for (String clazz : classPriors.keySet()) {
            double probability = classPriors.get(clazz);
            int classCount = classCounts.get(clazz);

            for (int i = 0; i < m; i++) {
                Map<String, Double> probabilities = classPosteriors.get(clazz).get(i);
                probability *= probabilities.getOrDefault(input[i], smooth(0, classCount, attributeValueSizes.get(i)));
            }

            if (bestClass == null || probability > bestProbability) {
                bestProbability = probability;
                bestClass = clazz;
            }
        }

        return bestClass;
    }
}