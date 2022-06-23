package org.lambda.utils;

import com.spun.util.MarkdownTable;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;

public class Grid
{
  public static String print(int width, int height, Function2<Integer, Integer, String> f2)
  {
    StringBuffer b = new StringBuffer();
    for (int y = 0; y < height; y++)
    {
      for (int x = 0; x < width; x++)
      {
        String c = f2.call(x, y);
        c = c == null ? " " : c;
        b.append(c + " ");
      }
      b.append("\n");
    }
    return b.toString();
  }
  public static String print(int width, int height, final String cell)
  {
    return print(width, height, (x, y) -> cell);
  }
  public static String printMarkdown(int width, int height, Function2<Integer, Integer, String> f2)
  {
    Queryable<Integer> numbers = Range.getAsQueryable(0, width - 1);
    Queryable<String> asQueryable = numbers.select(i -> "" + i);
    asQueryable.add(0, " ");
    MarkdownTable table = MarkdownTable.withHeaders(asQueryable.asArray());
    table.setColumnsConsistentWidth(true);
    for (int y = 0; y < height; y++) {
      int y2 = y;
      Queryable<String> row = numbers.select(x -> f2.call(x, y2)).select(c -> c == null ? "" : c);
      row.add(0, "**" + y + "**");
      table.addRow(row.toArray());
    }
    return table.toMarkdown();
//    StringBuffer b = new StringBuffer();
//    b.append(printHeader(width));
//    for (int y = 0; y < height; y++)
//    {
//      b.append("|**" + y + "**|");
//      for (int x = 0; x < width; x++)
//      {
//        String c = f2.call(x, y);
//        c = c == null ? " " : c;
//        b.append(" " + c + " |");
//      }
//      b.append("\n");
//    }
//    return b.toString();
  }
  private static String printHeader(int width)
  {
    StringBuffer b = new StringBuffer();
    b.append("|     |");
    for (int x = 0; x < width; ++x)
    {
      b.append(String.format(" %s |", x));
    }
    b.append("\n|--");
    for (int x = 0; x <= width; ++x)
    {
      b.append("---|");
    }
    b.append("\n");
    return b.toString();
  }
}
