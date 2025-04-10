package org.example.KNN;

public class Vector {
    public double[] features;
    public String label;

    public Vector(double[] features, String label) {
        this.features = features;
        this.label = label;
    }
}
