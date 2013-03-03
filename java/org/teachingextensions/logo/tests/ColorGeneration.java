package org.teachingextensions.logo.tests;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;

import com.spun.util.StringUtils;
import com.spun.util.Tuple;
import com.spun.util.io.FileUtils;
import com.spun.util.tests.TestUtils;
import com.spun.util.velocity.ContextAware.ContextAwareMap;
import com.spun.util.velocity.VelocityParser;

@UseReporter(TortoiseTextDiffReporter.class)
public class ColorGeneration extends TestCase
{
  public void testGeneration() throws Exception
  {
    HashMap<String, List<Tuple<String, String>>> loadColors = loadColors();
    Approvals.verify(generateColors(loadColors, "colors.java.template"));
  }
  public void testHtmlDisplay() throws Exception
  {
    HashMap<String, List<Tuple<String, String>>> loadColors = loadColors();
    Approvals.verifyHtml(generateColors(loadColors, "colors.html"));
  }
  public void ptestSortingDisplay() throws Exception
  {
    HashMap<String, List<Tuple<String, String>>> loadColors = loadColors();
    TestUtils.displayText(generateColors(loadColors, "colors.sorted.txt"));
  }
  private HashMap<String, List<Tuple<String, String>>> loadColors()
  {
    HashMap<String, List<Tuple<String, String>>> colors = new HashMap<String, List<Tuple<String, String>>>();
    String[] split = FileUtils.readFromClassPath(getClass(), "colors.txt").split("\n");
    String currentColor = "";
    for (String line : split)
    {
      String[] parts = StringUtils.stripWhiteSpace(line).split(" ");
      //System.out.println(line);
      if (parts.length == 1)
      {
        currentColor = parts[0];
      }
      else
      {
        add(colors, currentColor, parts[0], parts[1]);
      }
    }
    return colors;
  }
  private String generateColors(HashMap<String, List<Tuple<String, String>>> colors, String template)
  {
    ContextAwareMap aware = new ContextAwareMap("colors", colors);
    aware.put("finder", this);
    Object[] keys = colors.keySet().toArray();
    Arrays.sort(keys);
    aware.put("keys", keys);
    return VelocityParser.parseFromClassPath(this.getClass(), template, aware);
  }
  public static String getOtherColors(HashMap<String, List<Tuple<String, String>>> colors, String key, String color)
  {
    String out = "";
    for (Entry<String, List<Tuple<String, String>>> entry : colors.entrySet())
    {
      if (!entry.getKey().equals(key))
      {
        if (containsColor(entry.getValue(), color))
        {
          out += entry.getKey() + ", ";
        }
      }
    }
    return out;
  }
  private static boolean containsColor(List<Tuple<String, String>> value, String color)
  {
    for (Tuple<String, String> tuple : value)
    {
      if (color.equals(tuple.getFirst())) { return true; }
    }
    return false;
  }
  private void add(HashMap<String, List<Tuple<String, String>>> colors, String colorGroup, String name,
      String hexValue)
  {
    List<Tuple<String, String>> list = colors.get(colorGroup);
    if (list == null)
    {
      list = new ArrayList<Tuple<String, String>>();
      colors.put(colorGroup, list);
    }
    list.add(new Tuple<String, String>(name, hexValue));
    Collections.sort(list, new BrightnessComparator());
  }
  public class BrightnessComparator implements Comparator<Tuple<String, String>>
  {
    @Override
    public int compare(Tuple<String, String> o1, Tuple<String, String> o2)
    {
      Color c1 = Color.decode(o1.getSecond());
      Color c2 = Color.decode(o2.getSecond());
      Integer b1 = c1.getGreen() + c1.getRed() + c1.getBlue();
      Integer b2 = c2.getGreen() + c2.getRed() + c2.getBlue();
      return b1.compareTo(b2);
    }
  }
}
