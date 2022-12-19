import java.awt.Color;
import java.awt.BasicStroke;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.Vector;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.util.List;

public class XYLineChart_AWT extends ApplicationFrame {

    public XYLineChart_AWT(String applicationTitle, String title,List<FuzzySetData> fuzzysets ) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                title,
                "X-axis" ,
                "Y-axis" ,
                createDataset(fuzzysets) ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.MAGENTA );
        renderer.setSeriesPaint( 1 , Color.blue );
        renderer.setSeriesPaint( 2 , Color.GREEN );
        renderer.setSeriesPaint( 3 , Color.YELLOW );
        renderer.setSeriesPaint( 4 , Color.RED );
        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
        renderer.setSeriesStroke( 3 , new BasicStroke( 4.0f ) );
        renderer.setSeriesStroke( 4 , new BasicStroke( 3.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset(List<FuzzySetData> fuzzyset) {
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        for(FuzzySetData set:fuzzyset){
            final XYSeries firefox = new XYSeries( set.setName );
            if(set.type.equals("TRI")){
                firefox.add( (double) set.fuzzySetValues.get(0) , 0 );
                firefox.add( (double) set.fuzzySetValues.get(1) , 1.0 );
                firefox.add( (double) set.fuzzySetValues.get(2) , 0 );
            }
            if(set.type.equals("TRAP")){
                firefox.add( (double) set.fuzzySetValues.get(0) , 0 );
                firefox.add( (double) set.fuzzySetValues.get(1) , 1.0 );
                firefox.add( (double) set.fuzzySetValues.get(2) , 1.0 );
                firefox.add( (double) set.fuzzySetValues.get(3) , 0 );
            }
            dataset.addSeries( firefox );
        }
        return dataset;
    }

    public static void main( String[ ] args ) {
    }
}