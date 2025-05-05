package org.example.KMeans;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String[]> dataset = PrepareDataset.prepareDataset("src/main/resources/iris.csv");

        if(dataset == null){
            System.out.println("No dataset found");
            return;
        }

        List<double[]> clusters = new ArrayList<>();

        for(String[] row : dataset) {
            double[] cluster = new double[row.length - 1];

            for(int i = 0; i < row.length - 1; i++) {
                cluster[i] = Double.parseDouble(row[i]);
            }

            clusters.add(cluster);
        }

        var kMeans = new KMeans(3, clusters);

        SwingUtilities.invokeLater(() -> {
            Plot.plotClusters(clusters, kMeans.getClusterAndCentroid(), kMeans.getCentroids());
        });
    }
}
