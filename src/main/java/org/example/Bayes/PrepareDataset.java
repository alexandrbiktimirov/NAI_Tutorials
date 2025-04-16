package org.example.Bayes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PrepareDataset {
    public static List<String[]> prepareDataset(String path){
        List<String[]> dataset = new ArrayList<>();

        try(var lines = Files.lines(Path.of(path))){
            lines
                    .map(line -> line.split(" "))
                    .forEach(dataset::add);
        }catch (Exception e){
            System.out.println("File not found");
            return null;
        }

        List<String> trainDataSet = new ArrayList<>();
        List<String> testDataSet = new ArrayList<>();

        return dataset;
    }
}
