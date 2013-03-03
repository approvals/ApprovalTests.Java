package com.spun.util.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.spun.util.ArrayUtils;

public class TemplateArray
{
  private static String FILLER = "filler";
  /************************************************************************/
  public static Object[] join(Object part1[], Object part2[], String subsectionLabel, boolean forceIfEmpty)
  {
    if (part2 != null && part2.length == 0 && !forceIfEmpty) { return part1; }
    ArrayList<Object> total = new ArrayList<Object>();
    ArrayUtils.addArray(total, part1);
    total.add(new Subsection(subsectionLabel));
    ArrayUtils.addArray(total, part2);
    return total.toArray();
  }
  /***********************************************************************/
  public static List<String> getFillers(List list, int total)
  {
    ArrayList<String> newList = new ArrayList<String>(list);
    int needed = total - list.size();
    for (int i = 0; i < needed; i++)
    {
      newList.add(FILLER);
    }
    return newList;
  }
  /***********************************************************************/
  public static String addWhiteSpace(String text, int tabSize)
  {
    String whiteSpace = "                                                  ";
    tabSize = tabSize - text.length();
    tabSize = (tabSize < 0) ? 0 : tabSize;
    while (whiteSpace.length() < tabSize)
    {
      whiteSpace = whiteSpace + whiteSpace;
    }
    return whiteSpace.substring(0, tabSize);
  }
  /************************************************************************/
  public boolean isInitCall(String object, String params[])
  {
    String initOn[] = {"init", "size", "isEmpty"};
    return (object.equals("") && (params.length > 0) && Arrays.asList(initOn).contains(params[0]));
  }
  /************************************************************************/
  /*                            INNER CLASSES                             */
  /************************************************************************/
  public static class Subsection
  {
    public String sectionName = null;
    /************************************************************************/
    public Subsection(String sectionName)
    {
      super();
      this.sectionName = sectionName;
    }
    /************************************************************************/
    /************************************************************************/
  }
  /************************************************************************/
  /************************************************************************/
}