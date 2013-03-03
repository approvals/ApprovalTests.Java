package com.spun.util.parser;

/**
 * Import Note : Unlike much of java, 
 * this is not Zero indexed and is inclusive on both ends<BR>
 * i.e. <BR>
 * [1,2,3,4,5,6,7,8] with a section 
 * Start = 1 & SectionSize = 4] 
 * Has the following properties
 * Start = 1
 * Section Size = 4
 * End = 4
 * Total size = 8
 **/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListSectionInfo
{
  public int start       = 0;
  public int sectionSize = 0;
  public int totalSize   = 0;
  /************************************************************************/
  public ListSectionInfo(int start, int sectionSize, int totalSize)
  {
    this.totalSize = totalSize;
    this.start = start;
    if (start > totalSize)
    {
      this.sectionSize = 0;
    }
    else
    {
      this.sectionSize = ((start + sectionSize) > this.totalSize) ? this.totalSize - this.start + 1 : sectionSize;
    }
  }
  /************************************************************************/
  /** 
   * [Start,End] => [3,5] => 3,4,5
   * @return start point (inclusive)
   **/
  public int getStart()
  {
    return start;
  }
  /************************************************************************/
  /** 
   * [Start,End] => [3,5] => 3,4,5
   * @return end point (inclusive)
   **/
  public int getEnd()
  {
    return start + sectionSize - 1;
  }
  /************************************************************************/
  public int getTotalSize()
  {
    return totalSize;
  }
  /************************************************************************/
  public int getSectionSize()
  {
    return sectionSize;
  }
  /************************************************************************/
  public static ListSectionInfo getListSectionBySQL(int start, int sectionSize, String sql, Statement stmt)
      throws SQLException
  {
    ResultSet rsCount = stmt.executeQuery(sql);
    rsCount.next();
    // This is done without an if to throw an error if the expected result is not given
    return new ListSectionInfo(start, sectionSize, rsCount.getInt(1));
  }
  /************************************************************************/
  public String toString()
  {
    String value = "com.spun.util.parser.ListSection[";
    value += " start = " + start + ",\n" + " sectionSize = " + sectionSize + ",\n" + " getEnd = " + getEnd()
        + ",\n" + " totalSize = " + totalSize + "]";
    return value;
  }
  /************************************************************************/
  /************************************************************************/
}