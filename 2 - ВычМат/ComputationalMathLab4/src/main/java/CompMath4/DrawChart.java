package CompMath4;

import CompMath4.Computations.Functions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class DrawChart {
    public void draw(double[][] points, double[][] dataSetChart) {
        Functions functions = new Functions();

        XYSeries series = new XYSeries("Points");
        XYSeries series1 = new XYSeries("Fi_1");
        XYSeries series2 = new XYSeries("Fi_2");
        XYSeries series3 = new XYSeries("Fi_3");
        XYSeries series4 = new XYSeries("Fi_4");
        XYSeries series5 = new XYSeries("Fi_5");

        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < points[0].length; i++) {
            series.add(points[0][i], points[1][i]);
        }

        for (double i = points[0][0]; i <= points[0][points[0].length - 1]; i += 0.1) {
            series1.add(i, functions.f(i, dataSetChart[0][0], dataSetChart[0][1], 0, 1));
            if (functions.checkMinus(points)) {
                series2.add(i, functions.f(i, dataSetChart[1][0], dataSetChart[1][1], 0, 2));
                series3.add(i, functions.f(i, dataSetChart[2][0], dataSetChart[2][1], 0, 3));
                series4.add(i, functions.f(i, dataSetChart[3][0], dataSetChart[3][1], 0, 4));
            }
            series5.add(i, functions.f(i, dataSetChart[4][0], dataSetChart[4][1], dataSetChart[4][2], 5));
        }

        dataset.addSeries(series);
        dataset.addSeries(series1);
        if (functions.checkMinus(points)) {
            dataset.addSeries(series2);
            dataset.addSeries(series3);
            dataset.addSeries(series4);
        }
        dataset.addSeries(series5);

        JFreeChart chart = ChartFactory.createXYLineChart("Fi(x)", "x",
                "Y", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        JFrame frame = new JFrame("MinimalStaticChart");
        // ???????????????? ???????????? ???? ??????????
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }
}