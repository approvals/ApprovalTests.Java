package com.spun.util.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.objectplanet.chart.ChartSample;
import com.spun.util.MySystem;

/**
  * An Interface for graphic objects
  **/
public class LineChart
  implements ImageObject
{
  public Color colors[] = {new Color(55,19,153), new Color(74,26,204),
                           new Color(90,30,255), new Color(90,18,153),  
                           new Color(75,19,153), new Color(97,21,179)};
  private LineChartLine lines[] = null;
  private Dimension size = null;
  private String backGroundImage = null;
  private int redunanceId = -1;
  private String title = null;
  private Vector floatingLines = new Vector();
  
  /***********************************************************************/
  /**
    * 
    **/
  public LineChart(LineChartLine lines[], int width, int height ,
                  String backGroundImage)
  {
    size = new Dimension(width, height);
    this.lines = lines;
    this.backGroundImage = backGroundImage;
    this.redunanceId = ImageWriter.getImageId();
  }
  /***********************************************************************/
  /**
    * 
    **/
  public LineChart(int redunanceId)
  {
    this.redunanceId = redunanceId;
  }
  /***********************************************************************/
  public void write(OutputStream out)
  {
    new ImageWriter(this, out, ImageWriter.Encoding.GIF).start();
  }
  /***********************************************************************/
  /**
    * 
    **/
  public int getId()
  {
    return redunanceId;
  }
  /***********************************************************************/
  /**
    *
    **/
  public BufferedImage render()
  {
    return draw();
  }
  /***********************************************************************/
  public void setScale(int scale)
  {
  }
  /***********************************************************************/
  public void setTitle(String title)
  {
    this.title = title;
  }
  /***********************************************************************/
  public void addFloatingLine(LineChartLine line)
  {
    floatingLines.add(line);
  }
  /***********************************************************************/
  /**
    *
    **/
  public BufferedImage draw()
  {
    BufferedImage outImage = new BufferedImage(size.width,size.height,
                                               BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = outImage.createGraphics();
    com.objectplanet.chart.LineChart chart = drawChart(g2d);
    drawIcon(g2d);
    drawFlags(g2d, chart);
    g2d.dispose();
    return outImage;    
  }
  
  /***********************************************************************/
  /**
  	*
  	**/
  public com.objectplanet.chart.LineChart drawChart(Graphics2D graphics)
  {
  	// create the image	
  	Frame frame = new Frame();
	  frame.addNotify();
  	Image image = frame.createImage(size.width, size.height);
  	Graphics g = image.getGraphics();
 
  	//create the chart
  	com.objectplanet.chart.LineChart chart = new com.objectplanet.chart.LineChart();
    chart.setSeriesCount(lines.length);
    chart.setSampleCount(lines[0].getSize());
    for (int i = 0; i < lines.length; i++)
    {
//      My_System.variable("adding series " + i);
//      My_System.variable(lines[0].getChartSamples());
      chart.setSamples(i, lines[0].getChartSamples());
    }
    double maximum = chart.getMaxValue(-1);
    double minimum = chart.getMinValue(-1);
    
    ChartSample [] samples = lines[0].getChartSamples();
    
    for (int i = 0; i < lines[0].getSize(); i++)
    {
      chart.setSampleLabel(i, samples[i].getLabel());
    }
    
    double maxPosition = Math.ceil((maximum == 0) ?0 : (Math.log(Math.abs(maximum)) / Math.log(10)));
    double minPosition = Math.ceil((minimum == 0) ?0 : (Math.log(Math.abs(minimum)) / Math.log(10)));

    double roundTo = Math.pow(10, maxPosition) / 100 / 2;
    
    MySystem.variable("roundTo " + roundTo);
    MySystem.variable("max " + maximum);
    MySystem.variable("min " + minimum);
    MySystem.variable("max " + maxPosition);
    MySystem.variable("min " + minPosition);
    
    chart.setRange(roundTo(maximum, roundTo, true));
    chart.setLowerRange(roundTo(minimum, roundTo, false));
//    My_System.variable("minimum = " + minimum);
//    chart.setChartData(data);
//    chart.setMultiSeriesOn(true); 
//    chart.setBarAlignment(com.objectplanet.chart.BarChart.HORIZONTAL);
//    chart.setBarLabelsOn(true);
//    double max = data.getMaxValue(-1);
//    double min = data.getMinValue(-1);
//    double add = ((max - min) * 0.1);
//    max = Math.ceil(max) + 1;
//    min = Math.ceil(min - add);
//    chart.setRange(max);
//  	chart.setLowerRange(min );
  	chart.setBackground(new Color(0Xcc,0Xcc,0Xcc));
  	chart.setValueLinesColor(new Color(0X00,0X99,0Xcc));
    chart.set3DModeOn(false) ;
    chart.setLegendOn(false);
    if (title != null)
    {
      chart.setTitle(title);
    }
    chart.setTitleOn(true) ;
  	chart.setSize(size.width, size.height);
  	chart.setValueLinesOn(true);
  	chart.setSampleLabelsOn(true);
  	chart.setValueLabelsOn(false);
  	chart.setRangeLabelsOn(true);
    chart.setGraphInsets(-1,-1,40,-1);
  	chart.paint(g);
    
    MySystem.variable(chart.getGraphBounds().toString());
//    My_System.variable(chart.getSampleLabels());
    
    graphics.drawImage(image, new AffineTransform(1f,0f,0f,1f,0, 0), null);
    
    return chart;
  }
  /***********************************************************************/
  private double roundTo(double number, double roundTo, boolean roundUp)
  {
    int divider = (int) (number / roundTo);
    double remainder = number % roundTo;
    
    return (divider * roundTo) + ((roundUp && remainder > 0) ? roundTo : 0);
  }
  /***********************************************************************/
  /**
    *
    **/
  public void drawIcon(Graphics2D graphics)
  {
    //Clear Background
    if (backGroundImage != null)
    {
      Image backGround = new ImageIcon(backGroundImage).getImage();
      graphics.drawImage(backGround, new AffineTransform(1f,0f,0f,1f,0,size.height- backGround.getHeight(null)), null);  
    }
  }
  /***********************************************************************/
  /**
    *
    **/
  public void drawFlags(Graphics2D graphics, com.objectplanet.chart.LineChart chart)
  {
    Rectangle bounds = chart.getGraphBounds();
    for (int i = 0; i < lines.length; i++)
    {
      // does this line have flagged points?
      if (lines[i].hasFlags())
      {
        // if so then draw them
        drawLineFlags(graphics, chart, bounds, lines[i]);
      }
    }
    for (int i = 0; i < floatingLines.size(); i++)
    {
      // does this line have flagged points?
      LineChartLine line = (LineChartLine) floatingLines.get(i);
      if (line.hasFlags())
      {
        // if so then draw them
        drawLineFlags(graphics, chart, bounds, line);
      }
    }
  }
  /***********************************************************************/
  /**
    *
    **/
  private void drawLineFlags(Graphics2D graphics, com.objectplanet.chart.LineChart chart, Rectangle bounds, LineChartLine line)
  {
    LineChartPoint [] points = line.getValues();
    for (int i = 0; i < points.length; i++)
    {
      if (points[i].isFlagged())
      {
        int FLAG_SIZE = 8;
        // draw blob
        int yPixel = chart.getValuePosition(points[i].getY());
        int xPixelOffset = (int) (bounds.getWidth() * (double) i /  (double) (points.length - 1));
        int xPixel = (int) bounds.getX() + xPixelOffset;
        yPixel -= FLAG_SIZE / 2;
        xPixel -= FLAG_SIZE / 2;
//        My_System.variable("offset = " + xPixelOffset);
        graphics.setColor(points[i].getFlagColor());
        graphics.fillOval(xPixel, yPixel, FLAG_SIZE, FLAG_SIZE);
        if (points[i].getLabel() != null)
        {
          graphics.drawString(points[i].getLabel(), xPixel + 8, yPixel + 8);
        }
      }
    }
  }
  @Override
  public int hashCode()
  {
    return getId();
  }
  /***********************************************************************/
  public boolean equals(Object o)
  {
    if (o instanceof ImageObject)
    {
      return (getId() == ((ImageObject)o).getId());
    }
    else
    {
      return false;
    }
  }
  /***********************************************************************/
  public static void main(String agrs[])
  {
    LineChartLine line = new LineChartLine("title","valueString");
    line.addPoint(new LineChartPoint(1,110000,"hello!"));
    line.addPoint(new LineChartDatePoint(new Date(),180020, Color.black));
    line.addPoint(new LineChartDatePoint(new Date(),110090));
    
    LineChartLine lines[] = {line};
    LineChart chart = new LineChart(lines, 200,150,"c:\\my stuff\\www\\stockgazing\\images\\graph_logo.gif");
    JFrame frame = new JFrame();
    frame.getContentPane().add(new JLabel(new ImageIcon(chart.render())));
    frame.pack();
    com.spun.util.WindowUtils.testFrame(frame);
  }
  
  /***********************************************************************/
  /***********************************************************************/
}