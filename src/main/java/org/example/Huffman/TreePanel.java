package org.example.Huffman;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TreePanel extends JPanel {
    private static final int H_SPACING = 60;
    private static final int V_SPACING = 80;
    private static final int MARGIN_X = 50;
    private static final int MARGIN_Y = 50;

    Node root;

    public TreePanel(Node root) {
        this.root = root;
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
        if (node.isLeaf()) {
            leaves.add(node);
        } else {
            if (node.left != null) computeLeafY(node.left, depth + 1, leaves);
            if (node.right != null) computeLeafY(node.right, depth + 1, leaves);
        }
    }

    private void computeInternalX(Node node) {
        if (node.isLeaf()) return;
        if (node.left != null) computeInternalX(node.left);
        if (node.right != null) computeInternalX(node.right);
        node.x = (node.left.x + node.right.x) / 2;
    }

    private void drawNode(Graphics g, Node node) {
        if (node.left != null) {
            g.drawLine(node.x, node.y, node.left.x, node.left.y);
            g.drawString("0", (node.x + node.left.x) / 2, (node.y + node.left.y) / 2);
            drawNode(g, node.left);
        }
        if (node.right != null) {
            g.drawLine(node.x, node.y, node.right.x, node.right.y);
            g.drawString("1", (node.x + node.right.x) / 2, (node.y + node.right.y) / 2);
            drawNode(g, node.right);
        }

        FontMetrics fm = g.getFontMetrics();
        String text = node.symbol;
        int textWidth = fm.stringWidth(text);
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