package org.example.Bayes;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String[]> dataset = PrepareDataset.prepareDataset("src/main/resources/outGame.csv");

        if (dataset == null) {
            return;
        }

        Collections.shuffle(dataset);
        List<String[]> testDataset = new ArrayList<>(dataset.subList(0, 2));
        List<String[]> trainDataset = new ArrayList<>(dataset.subList(2, dataset.size()));
    }
}