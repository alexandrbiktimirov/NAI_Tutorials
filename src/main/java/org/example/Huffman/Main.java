package org.example.Huffman;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    static class Node {
        String symbol;
        int freq;
        Node left;
        Node right;
        int x, y;

        Node(String s, int f) {
            symbol = s;
            freq = f;
        }

        Node(String s, int f, Node l, Node r) {
            symbol = s;
            freq = f;
            left = l;
            right = r;
        }
    }

    public static Node buildTree(String text) {
        Map<Character,Integer> freqMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char lower = Character.toLowerCase(c);
                freqMap.put(lower, freqMap.getOrDefault(lower, 0) + 1);
            } else if (c == ' ') {
                freqMap.put(' ', freqMap.getOrDefault(' ', 0) + 1);
            }
        }
        List<Node> nodes = new ArrayList<>();
        for (Map.Entry<Character,Integer> e : freqMap.entrySet()) {
            char k = e.getKey();
            int f = e.getValue();
            String sym = (k == ' ') ? "_" : String.valueOf(k);
            nodes.add(new Node(sym, f));
        }
        Comparator<Node> cmp = (n1, n2) -> {
            if (n1.freq != n2.freq) return n1.freq - n2.freq;
            return n1.symbol.compareTo(n2.symbol);
        };
        nodes.sort(cmp);

        System.out.println("Initial frequencies:");
        for (Node n : nodes) {
            String out = n.symbol.equals("_") ? "space" : n.symbol;
            System.out.println(out + ": " + n.freq);
        }

        System.out.print("Step 0: ");
        printLineWithSubscripts(nodes);

        int step = 1;
        while (nodes.size() > 1) {
            Node first = nodes.removeFirst();
            Node second = nodes.removeFirst();
            String mergedSymbol = first.symbol + second.symbol;
            int mergedFreq = first.freq + second.freq;
            Node parent = new Node(mergedSymbol, mergedFreq, first, second);
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
        String digits = Integer.toString(number);
        StringBuilder sub = new StringBuilder();

        for (char d : digits.toCharArray()) {
            switch (d) {
                case '0': sub.append('₀'); break;
                case '1': sub.append('₁'); break;
                case '2': sub.append('₂'); break;
                case '3': sub.append('₃'); break;
                case '4': sub.append('₄'); break;
                case '5': sub.append('₅'); break;
                case '6': sub.append('₆'); break;
                case '7': sub.append('₇'); break;
                case '8': sub.append('₈'); break;
                case '9': sub.append('₉'); break;
            }
        }

        return sub.toString();
    }

    public static void buildCodes(Node node, String code, Map<String,String> map) {
        if (node.left == null && node.right == null) {
            map.put(node.symbol, code);
            return;
        }

        if (node.left != null)  buildCodes(node.left,  code + "1", map);
        if (node.right != null) buildCodes(node.right, code + "0", map);
    }

    static class TreePanel extends JPanel {
        private static final int H_SPACING = 60;
        private static final int V_SPACING = 80;
        private static final int MARGIN_X = 50;
        private static final int MARGIN_Y = 50;

        Node root;

        TreePanel(Node r) {
            root = r;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (root != null) {
                assignPositions(root);
                drawNode(g, root);
            }
        }

        private void assignPositions(Node node) {
            List<Node> leaves = new ArrayList<>();
            computeLeafY(node, 0, leaves);
            for (int i = 0; i < leaves.size(); i++) {
                leaves.get(i).x = MARGIN_X + i * H_SPACING;
            }
            computeInternalX(node);
        }

        private void computeLeafY(Node node, int depth, List<Node> leaves) {
            node.y = MARGIN_Y + depth * V_SPACING;
            if (node.left == null && node.right == null) {
                leaves.add(node);
            } else {
                if (node.left != null)  computeLeafY(node.left,  depth + 1, leaves);
                if (node.right != null) computeLeafY(node.right, depth + 1, leaves);
            }
        }

        private void computeInternalX(Node node) {
            if (node.left == null && node.right == null) {
                return;
            }
            if (node.left != null)  computeInternalX(node.left);
            if (node.right != null) computeInternalX(node.right);
            node.x = (node.left.x + node.right.x) / 2;
        }

        private void drawNode(Graphics g, Node node) {
            if (node.left != null) {
                g.drawLine(node.x, node.y, node.left.x, node.left.y);
                int lx = (node.x + node.left.x) / 2;
                int ly = (node.y + node.left.y) / 2;
                g.drawString("1", lx, ly);
                drawNode(g, node.left);
            }
            if (node.right != null) {
                g.drawLine(node.x, node.y, node.right.x, node.right.y);
                int lx = (node.x + node.right.x) / 2;
                int ly = (node.y + node.right.y) / 2;
                g.drawString("0", lx, ly);
                drawNode(g, node.right);
            }

            String text = node.symbol;
            FontMetrics fm = g.getFontMetrics();
            int textWidth  = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int nodeW = textWidth + 10;
            int nodeH = textHeight + 4;
            int topLeftX = node.x - nodeW / 2;
            int topLeftY = node.y - nodeH / 2;
            g.drawOval(topLeftX, topLeftY, nodeW, nodeH);

            int baselineY = node.y + (fm.getAscent() - textHeight / 2);
            g.drawString(text, node.x - textWidth / 2, baselineY);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        Node root = buildTree(text);

        Map<String,String> codeMap = new TreeMap<>();
        buildCodes(root, "", codeMap);

        System.out.println("Encoded values:");
        for (Map.Entry<String,String> e : codeMap.entrySet()) {
            String sym = e.getKey().equals("_") ? "space" : e.getKey();
            System.out.println(sym + ": " + e.getValue());
        }

        JFrame frame = new JFrame("Huffman Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TreePanel(root));
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}