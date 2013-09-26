package com.spun.util.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultSetWriter
{
  public static String toString(ResultSet rs) throws SQLException
  {
    List<String[]> results = extractResults(rs);
    List<String> metaData = extractMetaData(rs);
    StringBuffer sb = new StringBuffer();
    sb.append(metaData.toString() + "\r\n");
    for (String[] strings : results)
    {
      sb.append(Arrays.toString(strings) + "\r\n");
    }
    return sb.toString();
  }
  /***********************************************************************/
  public static List<String[]> extractResults(ResultSet rs) throws SQLException
  {
    int columns = rs.getMetaData().getColumnCount();
    ArrayList<String[]> found = new ArrayList<String[]>();
    while (rs.next())
    {
      String rowData[] = new String[columns];
      for (int i = 1; i <= columns; i++)
      {
        rowData[i - 1] = rs.getString(i);
      }
      found.add(rowData);
    }
    return found;
  }
  /***********************************************************************/
  public static List<String> extractMetaData(ResultSet rs) throws SQLException
  {
    ResultSetMetaData meta = rs.getMetaData();
    ArrayList<String> titles = new ArrayList<String>(meta.getColumnCount());
    for (int i = 1; i <= meta.getColumnCount(); i++)
    {
      titles.add(meta.getColumnName(i));
    }
    return titles;
  }
}
