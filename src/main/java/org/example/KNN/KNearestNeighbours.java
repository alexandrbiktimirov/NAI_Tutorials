package org.example.KNN;

import java.util.*;

public class KNearestNeighbours {
    private final int k;
    private final List<Vector> trainDataset;
    private final Random random;

    public KNearestNeighbours(int k, List<Vector> trainDataset) {
        this.k = k;
        this.trainDataset = trainDataset;
        this.random = new Random();
    }

    public double calculateEuclideanDistance(double[] a, double[] b) {
        double sum = 0;

        for (int i = 0; i < a.length; i++) {
            double difference = a[i] - b[i];
            sum += Math.pow(difference, 2);
        }

        return Math.sqrt(sum);
    }

    public void sortDistances(double[] distances, int[] indices) {
        int n = distances.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (distances[j] > distances[j + 1]) {
                    double tempDistance = distances[j];
                    distances[j] = distances[j + 1];
                    distances[j + 1] = tempDistance;

                    int tempIndex = indices[j];
                    indices[j] = indices[j + 1];
                    indices[j + 1] = tempIndex;

                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }

    public String findPredictedClass(int[] indices, int k) {
        Map<String, Integer> frequency = new HashMap<>();

        for (int i = 0; i < k; i++) {
            String lab = trainDataset.get(indices[i]).label;
            frequency.put(lab, frequency.getOrDefault(lab, 0) + 1);
        }

        int maxCount = 0;
        for (int count : frequency.values()) {
            if (count > maxCount){
                maxCount = count;
            }
        }

        List<String> candidates = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() == maxCount) {
                candidates.add(entry.getKey());
            }
        }

        if (candidates.size() == 1) {
            return candidates.getFirst();
        } else {
            return candidates.get(random.nextInt(candidates.size()));
        }
    }

    public String predict(double[] observation) {
        int n = trainDataset.size();
        double[] distances = new double[n];
        int[] indices = new int[n];

        for (int i = 0; i < n; i++) {
            distances[i] = calculateEuclideanDistance(observation, trainDataset.get(i).features);
            indices[i] = i;
        }

        sortDistances(distances, indices);

        return findPredictedClass(indices, k);
    }
}