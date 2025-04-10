package org.example.Perceptron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class PrepareDataset {
    public static List<Vector> loadIrisDataset(String filename) {
        List<Vector> dataset = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));

            String line;
            int lineCount = 0;

            while((line = in.readLine()) != null){
                if(lineCount == 0){
                    lineCount++;
                    continue;
                }

                lineCount++;
                String[] parts = line.split(",");
                if(parts.length < 5){
                    continue;
                }
                String labelStr = parts[4].trim();
                if(!(labelStr.equals("setosa") || labelStr.equals("versicolor"))){
                    continue;
                }

                double[] features = new double[4];
                for(int i = 0; i < 4; i++){
                    features[i] = Double.parseDouble(parts[i]);
                }

                String label = labelStr.equals("setosa") ? "1" : "0";
                dataset.add(new Vector(features, label));
            }

            in.close();
        } catch(IOException e){
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

        for(String key : map.keySet()){
            List<Vector> list = map.get(key);
            Collections.shuffle(list);

            int total = list.size();
            int trainCount = (int)Math.round(total * 0.7);

            for(int i = 0; i < total; i++){
                if(i < trainCount){
                    train.add(list.get(i));
                } else {
                    test.add(list.get(i));
                }
            }
        }

        Vector[] trainArray = new Vector[train.size()];
        Vector[] testArray = new Vector[test.size()];

        for(int i = 0; i < train.size(); i++){
            trainArray[i] = train.get(i);
        }
        for(int i = 0; i < test.size(); i++){
            testArray[i] = test.get(i);
        }

        Vector[][] result = new Vector[2][];
        result[0] = trainArray;
        result[1] = testArray;

        return result;
    }
}