package org.example.KNN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PrepareDataset {
    public static List<Vector> loadIrisDataset(String filename) {
        List<Vector> dataset = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                if (lineCount == 0) {
                    lineCount++;
                    continue;
                }

                lineCount++;

                String[] parts = line.split(",");

                if (parts.length < 5) continue;

                double[] features = new double[4];

                for (int i = 0; i < 4; i++) {
                    features[i] = Double.parseDouble(parts[i]);
                }

                String label = parts[4];
                dataset.add(new Vector(features, label));
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return dataset;
    }

    public static Vector[][] trainTestSplit(List<Vector> dataset) {
        Map<String, List<Vector>> map = new HashMap<>();

        for (Vector v : dataset) {
            if (!map.containsKey(v.label)) {
                map.put(v.label, new ArrayList<>());
            }

            map.get(v.label).add(v);
        }

        List<Vector> train = new ArrayList<>();
        List<Vector> test = new ArrayList<>();

        for (List<Vector> list : map.values()) {
            Collections.shuffle(list);
            int total = list.size();
            int trainCount = (int) Math.round(total * 2.0 / 3.0);

            for (int i = 0; i < total; i++) {
                if (i < trainCount) {
                    train.add(list.get(i));
                } else {
                    test.add(list.get(i));
                }
            }
        }

        return new Vector[][] {train.toArray(new Vector[0]), test.toArray(new Vector[0])};
    }
}
