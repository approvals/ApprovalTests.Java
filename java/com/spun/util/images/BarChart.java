package com.spun.util.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.objectplanet.chart.*;
import com.spun.util.ObjectUtils;

/**
 * An Interface for graphic objects
 **/
public class BarChart implements ImageObject
{
  public static final int HORIZONTAL      = 1;
  public static final int VERITCAL        = 2;
  public Color            colors[]        = {new Color(55, 19, 153),
      new Color(74, 26, 204),
      new Color(90, 30, 255),
      new Color(90, 18, 153),
      new Color(75, 19, 153),
      new Color(97, 21, 179)              };
  private int             position        = 0;
  private BarChartLine    lines[]         = null;
  private Dimension       size            = null;
  private String          backGroundImage = null;
  private int             redunanceId     = -1;
  /***********************************************************************/
  /**
   * 
   **/
  public BarChart(BarChartLine lines[], int position, int width, int height, String backGroundImage)
  {
    size = new Dimension(width, height);
    this.position = position;
    this.lines = lines;
    this.backGroundImage = backGroundImage;
    this.redunanceId = ImageWriter.getImageId();
  }
  /***********************************************************************/
  /**
   * 
   **/
  public BarChart(int redunanceId)
  {
    this.redunanceId = redunanceId;
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
  /**
   *
   **/
  public BufferedImage draw()
  {
    BufferedImage outImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = outImage.createGraphics();
    drawChart(g2d);
    drawIcon(g2d);
    g2d.dispose();
    return outImage;
  }
  /***********************************************************************/
  /**
   *
   **/
  public void drawChart(Graphics2D graphics)
  {
    // create the image	
    Frame frame = new Frame();
    frame.addNotify();
    Image image = frame.createImage(size.width, size.height);
    Graphics g = image.getGraphics();
    //create the chart
    com.objectplanet.chart.BarChart chart = new com.objectplanet.chart.BarChart(lines.length);
    ChartData data = new ChartData(1, lines.length);
    for (int i = 0; i < lines.length; i++)
    {
      ChartSample sample = new ChartSample(0);
      sample.setLabel(lines[i].getTitle());
      sample.setValue(lines[i].getValue());
      //My_System.variable("sample = " + sample.toString());
      data.setSampleLabel(i, sample.getLabel());
      data.setSample(0, i, sample);
    }
    chart.setChartData(data);
    chart.setMultiSeriesOn(true);
    chart.setBarAlignment(com.objectplanet.chart.BarChart.HORIZONTAL);
    double max = data.getMaxValue(-1);
    double min = data.getMinValue(-1);
    double add = ((max - min) * 0.1);
    max = Math.ceil(max) + 1;
    min = Math.ceil(min - add);
    chart.setRange(max);
    chart.setLowerRange(min);
    chart.setBackground(new Color(0Xcc, 0Xcc, 0Xcc));
    chart.setValueLinesColor(new Color(0X00, 0X99, 0Xcc));
    chart.setTitle("StockGazing Top 5");
    chart.setTitleOn(false);
    chart.setBarLabelsOn(false);
    chart.setRangeLabelsOn(false);
    chart.setValueLinesOn(false);
    chart.setValueLabelsOn(false);
    chart.set3DModeOn(false);
    chart.setLegendOn(false);
    chart.setSize(size.width, size.height);
    chart.paint(g);
    graphics.drawImage(image, new AffineTransform(1f, 0f, 0f, 1f, 0, 0), null);
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
      graphics.drawImage(backGround, new AffineTransform(1f, 0f, 0f, 1f, 0, size.height
          - backGround.getHeight(null)), null);
    }
  }
  /***********************************************************************/
  public int hashCode()
  {
    return ObjectUtils.generateHashCode(getId());
  }
  /***********************************************************************/
  public boolean equals(Object o)
  {
    if (o instanceof ImageObject)
    {
      return (getId() == ((ImageObject) o).getId());
    }
    else
    {
      return false;
    }
  }
  /***********************************************************************/
  public static void main(String agrs[])
  {
    BarChartLine lines[] = {new BarChartLine(5.5, "five", "10%"),
        new BarChartLine(1, "one", "20%"),
        new BarChartLine(-4.5, "four-", "Thrity")};
    BarChart chart = new BarChart(lines, HORIZONTAL, 200, 150, null);//"c:\\my stuff\\www\\stockgazing\\images\\graph_logo.gif");
    JFrame frame = new JFrame();
    frame.getContentPane().add(new JLabel(new ImageIcon(chart.render())));
    frame.pack();
    com.spun.util.WindowUtils.testFrame(frame);
  }
  /***********************************************************************/
  public void write(OutputStream out)
  {
    new ImageWriter(this, out, ImageWriter.Encoding.GIF).start();
  }
  /***********************************************************************/
  /***********************************************************************/
  public int getPosition()
  {
    return position;
  }
}

class RelativeSizes
{
  public int eastPadding   = 5;
  public int westPadding   = 20;
  public int northPadding  = 50;
  public int southPadding  = 50;
  public int textPadding   = 5;
  public int startOfGraphX = 60;
  public int textSize      = 12;
  public int barHeight     = 20;
  public int barPadding    = 20;
  /***********************************************************************/
  public RelativeSizes(Dimension size, int lineNum)
  {
    int middleSpace = size.height - northPadding - southPadding;
    int average = middleSpace / ((lineNum * 2) + 1);
    barPadding = average;
  }
  /***********************************************************************/
  public int getYForLine(int number, int imageHeight)
  {
    int y = southPadding + ((number) * barHeight) + ((number) * barPadding);
    y = imageHeight - y;
    return y;
  }
  /***********************************************************************/
  public String toString()
  {
    return "RelativeSizes [eastPadding = " + eastPadding + "," + "westPadding = " + westPadding + ","
        + "northPadding = " + northPadding + "," + "southPadding = " + southPadding + "," + "textPadding = "
        + textPadding + "," + "startOfGraphX = " + startOfGraphX + "," + "textSize = " + textSize + ","
        + "barHeight = " + barHeight + "," + "barPadding = " + barPadding + "]";
  }
  /***********************************************************************/
  /***********************************************************************/
}