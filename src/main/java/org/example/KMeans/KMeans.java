package org.example.KMeans;

import java.util.*;

public class KMeans {
    private final int k;
    private final List<double[]> clusters;
    private final double[][] centroids;
    private final Map<double[], Integer> clusterAndCentroid = new HashMap<>();

    public KMeans(int k, List<double[]> clusters) {
        this.k = k;
        this.clusters = clusters;
        centroids = new double[k][clusters.getFirst().length];

        boolean containsAllK = false;

        while(!containsAllK) {
            Random random = new Random();

            for (double[] cluster : clusters) {
                clusterAndCentroid.put(cluster, random.nextInt(k));
            }

            Set<Integer> assignedValues = new HashSet<>(clusterAndCentroid.values());

            if (assignedValues.size() == k) {
                containsAllK = true;
            }
        }

        computeCentroids();
        train();
    }

    private void computeCentroids() {
        int dimensions = clusters.getFirst().length;
        int[] counts = new int[k];

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < dimensions; j++) {
                centroids[i][j] = 0.0;
            }

            counts[i] = 0;
        }

        for (var entry : clusterAndCentroid.entrySet()) {
            double[] point = entry.getKey();
            int clusterIndex = entry.getValue();

            for (int d = 0; d < dimensions; d++) {
                centroids[clusterIndex][d] += point[d];
            }

            counts[clusterIndex]++;
        }

        for (int i = 0; i < k; i++) {
            if (counts[i] == 0) continue;

            for (int d = 0; d < dimensions; d++) {
                centroids[i][d] /= counts[i];
            }
        }
    }

    private void train(){
        boolean newCentroidsChange = true;
        int epoch = 0;

        while(newCentroidsChange) {
            double[][] previousCentroids = new double[k][];
            for (int i = 0; i < k; i++) {
                previousCentroids[i] = centroids[i].clone();
            }

            System.out.println("Epoch #" + epoch);

            for (double[] cluster : clusters) {
                clusterAndCentroid.put(cluster, findClosestCentroid(cluster, centroids));
            }

            computeCentroids();

            if (Arrays.deepEquals(previousCentroids, centroids)) {
                newCentroidsChange = false;


                for (int i = 0; i < k; i++) {
                    List<double[]> clusters = new ArrayList<>();

                    for (var entry : clusterAndCentroid.entrySet()) {
                        if (entry.getValue() == i){
                            clusters.add(entry.getKey());
                        }
                    }

                    System.out.println("Within Cluster Sum of Squares for centroid #" + i + " " + Arrays.toString(centroids[i]) + " " + " is: " + EvaluationMetrics.wcss(clusters, centroids[i]));
                    clusters.clear();
                }
            }
            epoch++;
        }
    }

    public static int findClosestCentroid(double[] vector, double[][] centroids) {
        double[] distances = new double[centroids.length];

        double minimalDistance = Double.POSITIVE_INFINITY;
        List<Integer> minimalIndices = new ArrayList<>();

        for (int i = 0; i < centroids.length; i++) {
            distances[i] = calculateEuclideanDistance(vector, centroids[i]);

            if (distances[i] < minimalDistance) {
                minimalDistance = distances[i];
                minimalIndices.clear();
                minimalIndices.add(i);
            } else if (distances[i] == minimalDistance) {
                minimalIndices.add(i);
            }
        }

        Random random = new Random();
        return minimalIndices.get(random.nextInt(minimalIndices.size()));
    }


    public static double calculateEuclideanDistance(double[] a, double[] b) {
        double sum = 0;

        for (int i = 0; i < a.length; i++) {
            double difference = a[i] - b[i];
            sum += Math.pow(difference, 2);
        }

        return Math.sqrt(sum);
    }

    public List<double[]> getClusters() {
        return clusters;
    }

    public double[][] getCentroids() {
        return centroids;
    }

    public Map<double[], Integer> getClusterAndCentroid() {
        return clusterAndCentroid;
    }
}
