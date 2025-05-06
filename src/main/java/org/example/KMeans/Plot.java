package org.example.KMeans;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.List;
import java.util.Map;

public class Plot {
    private static final String[] FEATURES = {
            "Sepal Length", "Sepal Width", "Petal Length", "Petal Width"
    };

    public static void plotClusters(
            List<double[]> clusters,
            Map<double[], Integer> clusterAndCentroid,
            double[][] centroids
    ) {
        int dimensions = centroids[0].length;
        int k = centroids.length;

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(dimensions - 1, dimensions - 1));

        for (int i = 0; i < dimensions; i++) {
            for (int j = i + 1; j < dimensions; j++) {
                XYSeriesCollection clusterDs = new XYSeriesCollection();
                XYSeriesCollection centroidDs = new XYSeriesCollection();

                for (int c = 0; c < k; c++) {
                    XYSeries observations = new XYSeries("Cluster " + c);

                    for (double[] observation : clusters) {
                        if (clusterAndCentroid.get(observation) == c) {
                            observations.add(observation[i], observation[j]);
                        }
                    }

                    clusterDs.addSeries(observations);

                    XYSeries centroid = new XYSeries("Centroid " + c);
                    centroid.add(centroids[c][i], centroids[c][j]);
                    centroidDs.addSeries(centroid);
                }

                JFreeChart chart = ChartFactory.createScatterPlot(
                        FEATURES[i] + " vs " + FEATURES[j],
                        FEATURES[i],
                        FEATURES[j],
                        clusterDs,
                        PlotOrientation.VERTICAL,
                        true,
                        false,
                        false
                );

                XYPlot plot = chart.getXYPlot();
                plot.setDataset(1, centroidDs);
                plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

                XYLineAndShapeRenderer clusterR = new XYLineAndShapeRenderer(false, true);
                for (int s = 0; s < k; s++) {
                    clusterR.setSeriesShape(s, new Ellipse2D.Double(-3, -3, 6, 6));
                    clusterR.setSeriesStroke(s, new BasicStroke(1.0f));
                }

                XYLineAndShapeRenderer centroidR = new XYLineAndShapeRenderer(false, true);
                GeneralPath xShape = new GeneralPath();
                xShape.moveTo(-5, -5);
                xShape.lineTo(5, 5);
                xShape.moveTo(-5, 5);
                xShape.lineTo(5, -5);

                for (int s = 0; s < k; s++) {
                    centroidR.setSeriesShape(s, xShape);
                    centroidR.setSeriesStroke(s, new BasicStroke(90.0f));
                }

                plot.setRenderer(0, clusterR);
                plot.setRenderer(1, centroidR);

                frame.add(new ChartPanel(chart));
            }
        }

        frame.pack();
        frame.setVisible(true);
    }
}