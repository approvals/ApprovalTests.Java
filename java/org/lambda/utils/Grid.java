package org.lambda.utils;

import org.lambda.functions.Function2;

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
}
