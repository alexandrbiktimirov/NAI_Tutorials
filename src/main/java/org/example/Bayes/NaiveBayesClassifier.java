package org.example.Bayes;

import java.util.List;

public class NaiveBayesClassifier {
    private final boolean applySmoothingAll;
    private final List<String> trainDataset;

    public NaiveBayesClassifier(boolean applySmoothingAll, List<String> trainDataset) {
        this.applySmoothingAll = applySmoothingAll;
        this.trainDataset = trainDataset;
    }
}
