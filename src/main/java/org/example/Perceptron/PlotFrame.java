package org.example.Perceptron;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.JFrame;
import java.awt.geom.Ellipse2D;

public class PlotFrame extends JFrame {
    public PlotFrame(String title, Vector[] testSet, Perceptron perceptron, double mean0, double mean1) {
        super(title);

        XYSeries series1 = new XYSeries("setosa");
        XYSeries series2 = new XYSeries("versicolor");

        double minX = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;

        for (Vector vector : testSet) {
            double x = vector.features[2];
            double y = vector.features[3];

            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (vector.label.equals("1")) {
                series1.add(x, y);
            } else {
                series2.add(x, y);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        //w0 * mean0 + w1 * mean1 + w2 * x2 + w3 * x3 = threshold
        //x3 = -(w2/w3)*x2 + (threshold - (w0*mean0 + w1*mean1))/w3
        double alpha = 0;
        double beta = 0;
        if (perceptron.weights[3] != 0) {
            alpha = -perceptron.weights[2] / perceptron.weights[3]; //slope
            beta = (perceptron.threshold - (perceptron.weights[0] * mean0 + perceptron.weights[1] * mean1)) / perceptron.weights[3]; //y-intercept
        }

        XYSeries seriesLine = new XYSeries("Decision Boundary");

        //w0 * mean0 + w1 * mean1 + w2 * x2 + w3 * x3 = threshold
        double x1 = minX;
        double y1 = alpha * x1 + beta;
        double x2 = maxX;
        double y2 = alpha * x2 + beta;

        seriesLine.add(x1, y1);
        seriesLine.add(x2, y2);
        dataset.addSeries(seriesLine);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Decision Hyperplane",
                "Petal Length",
                "Petal Width",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, false);

        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6));
        renderer.setSeriesShape(1, new Ellipse2D.Double(-3, -3, 6, 6));

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}