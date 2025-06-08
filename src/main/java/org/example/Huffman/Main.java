package org.example.Huffman;

import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        Node root = HuffmanTreeBuilder.buildTree(text);

        Map<String, String> codeMap = new TreeMap<>();
        HuffmanCodeGenerator.buildCodes(root, "", codeMap);

        System.out.println("Encoded values:");
        for (Map.Entry<String, String> entry : codeMap.entrySet()) {
            String symbol = entry.getKey().equals("_") ? "space" : entry.getKey();
            System.out.println(symbol + ": " + entry.getValue());
        }

        JFrame frame = new JFrame("Huffman Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TreePanel(root));
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}