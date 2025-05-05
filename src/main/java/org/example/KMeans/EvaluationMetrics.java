package org.example.KMeans;

import java.util.List;

public class EvaluationMetrics {
    public static double wcss(List<double[]> clusters, double[] centroid){
        double sum = 0.0;

        for (double[] cluster : clusters) {
            sum += Math.pow(KMeans.calculateEuclideanDistance(cluster, centroid), 2);
        }

        return sum;
    }
}
