package com.spun.util.database.management;

import java.sql.Connection;
import java.sql.SQLException;
import com.spun.util.DatabaseUtils;

public class Sequence
{
  private String  name;
  private boolean exists;
  /************************************************************************/
  public Sequence(String name)
  {
    this.name = name;
  }
  /** **********************************************************************/
  public static Sequence extractSequence(String columnDefinition, String tableName, String columnName, Connection con) throws SQLException
  {
    if (columnDefinition == null)
    {
      return null;
    }
    else
    {
      Sequence seq = null;
      switch (DatabaseUtils.getDatabaseType(con))
      {
        case DatabaseUtils.POSTGRESQL :
          seq = extractPostgresSequence(columnDefinition, tableName, columnName);
          break;
      }
      if (seq != null)
      {
        seq.checkExistence(con);
      }
      return seq;
    }
  }
  /************************************************************************/
  private void checkExistence(Connection con) throws SQLException
  {
    java.sql.ResultSet rs = con.getMetaData().getTables(null, null, name, new String[]{"SEQUENCE"});
    this.exists = rs.next();
    rs.close();
  }
  /************************************************************************/
  private static Sequence extractPostgresSequence(String columnDefinition, String tableName, String columnName)
  {
    // nextval('"public.company_pkey_seq"'::text)
    String[] wrappers = {"nextval(\'\"", "\"\'::text)", 
                         "nextval(\'", "\'::text)", 
                         "nextval((\'\"","\"\'::text)::regclass)",
                         "nextval(\'", "\'::regclass)"};
    String seq = null;
    for (int i = 0; i < wrappers.length; i += 2)
    {
      String start = wrappers[i];
      String end = wrappers[i + 1];
      if (columnDefinition.startsWith(start) && columnDefinition.endsWith(end))
      {
        seq = columnDefinition.substring(start.length(), columnDefinition.length() - end.length());
        break;
      }
    }
    String prefix = "public.";
    if (seq != null && seq.startsWith(prefix))
    {
      seq = seq.substring(prefix.length());
    }
    //    else
    //    {
    //      throw new Error("unknown column definition[table, column, definition] = [" + tableName + ", " + columnName + "," + columnDefinition + "]");
    //    }
    //My_System.variable(String.format("[Column Definition, sequence] = [%s,%s]", columnDefinition, seq));
    return seq == null ? null : new Sequence(seq);
  }
  /************************************************************************/
  public boolean isExists()
  {
    return exists;
  }
  /************************************************************************/
  public String getName()
  {
    return name;
  }
  /************************************************************************/
  /************************************************************************/
}
