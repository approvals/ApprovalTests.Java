package com.spun.util.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.spun.util.ObjectUtils;

public class ResultSetWriter
{
  public static String toString(ResultSet rs) throws SQLException
  {
    List<String[]> results = extractResults(rs);
    List<String> metaData = extractMetaData(rs);
    StringBuffer sb = new StringBuffer();
    sb.append(metaData.toString() + "\n");
    for (String[] strings : results)
    {
      sb.append(Arrays.toString(strings) + "\n");
    }
    return sb.toString();
  }
  
  public static List<String[]> extractResults(ResultSet rs)
  {
    try
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
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  
  public static List<String> extractMetaData(ResultSet rs)
  {
    try
    {
      ResultSetMetaData meta = rs.getMetaData();
      ArrayList<String> titles = new ArrayList<String>(meta.getColumnCount());
      for (int i = 1; i <= meta.getColumnCount(); i++)
      {
        titles.add(meta.getColumnName(i));
      }
      return titles;
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
