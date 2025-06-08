package org.example.Huffman;

import java.util.*;
import java.util.List;

public class HuffmanTreeBuilder {

    public static Node buildTree(String text) {
        Map<Character,Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char lower = Character.toLowerCase(c);
                frequencyMap.put(lower, frequencyMap.getOrDefault(lower, 0) + 1);
            } else if (c == ' ') {
                frequencyMap.put(' ', frequencyMap.getOrDefault(' ', 0) + 1);
            }
        }

        List<Node> nodes = new ArrayList<>();
        for (var e : frequencyMap.entrySet()) {
            String sym = (e.getKey() == ' ') ? "_" : String.valueOf(e.getKey());
            nodes.add(new Node(sym, e.getValue()));
        }
        Comparator<Node> cmp = Comparator
                .comparingInt((Node n) -> n.freq)
                .thenComparing(Node::getMinSymbol);
        nodes.sort(cmp);

        System.out.println("Initial frequencies:");
        for (Node n : nodes) {
            System.out.println((n.symbol.equals("_") ? "space" : n.symbol) + ": " + n.freq);
        }
        System.out.print("Step 0: ");
        printLineWithSubscripts(nodes);

        int step = 1;
        while (nodes.size() > 1) {
            Node left  = nodes.removeFirst();
            Node right = nodes.removeFirst();
            Node parent = new Node(
                    left.symbol + right.symbol,
                    left.freq   + right.freq,
                    left, right
            );
            nodes.add(parent);
            nodes.sort(cmp);

            System.out.print("Step " + step + ": ");
            printLineWithSubscripts(nodes);
            step++;
        }

        return nodes.getFirst();
    }

    private static void printLineWithSubscripts(List<Node> list) {
        StringBuilder sb = new StringBuilder();
        for (Node n : list) {
            sb.append(n.symbol).append(toSubscript(n.freq));
        }
        System.out.println(sb);
    }

    private static String toSubscript(int number) {
        return Integer.toString(number)
                .replace("0", "₀").replace("1", "₁").replace("2", "₂").replace("3", "₃")
                .replace("4", "₄").replace("5", "₅").replace("6", "₆")
                .replace("7", "₇").replace("8", "₈").replace("9", "₉");
    }
}
