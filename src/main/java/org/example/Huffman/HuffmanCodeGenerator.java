package org.example.Huffman;

import java.util.Map;

public class HuffmanCodeGenerator {
    public static void buildCodes(Node node, String code, Map<String, String> map) {
        if (node.isLeaf()) {
            map.put(node.symbol, code);
            return;
        }

        if (node.left != null) buildCodes(node.left, code + "0", map);
        if (node.right != null) buildCodes(node.right, code + "1", map);
    }
}
