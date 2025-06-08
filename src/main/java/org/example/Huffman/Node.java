package org.example.Huffman;

public class Node {
    String symbol;
    int freq;
    Node left;
    Node right;
    int x, y;

    public Node(String symbol, int freq) {
        this.symbol = symbol;
        this.freq = freq;
    }

    public Node(String symbol, int freq, Node left, Node right) {
        this.symbol = symbol;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public String getMinSymbol() {
        if (isLeaf()) return symbol;
        String l = left  != null ? left.getMinSymbol()  : symbol;
        String r = right != null ? right.getMinSymbol() : symbol;
        return l.compareTo(r) <= 0 ? l : r;
    }
}