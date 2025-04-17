package org.example.Bayes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PrepareDataset {
    public static List<String[]> prepareDataset(String path) {
        List<String[]> dataset = new ArrayList<>();

        try (var lines = Files.lines(Path.of(path))) {
            lines
                    .skip(1)
                    .filter(line -> !line.trim().isEmpty())
                    .forEach(line -> dataset.add(line.split(" ")));
        } catch (IOException e) {
            System.out.println("File not found");
            return null;
        }

        return dataset;
    }
}