package com.spun.util.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.spun.util.logger.SimpleLogger;

public class ListSection
{
  private List<Object> section       = null;
  private int          startingPoint = 0;
  private int          overAllSize   = 0;
  /************************************************************************/
  public ListSection(List<Object> section, int startingPoint, int overAllSize)
  {
    this.startingPoint = startingPoint;
    this.overAllSize = overAllSize;
    this.section = section;
  }
  /************************************************************************/
  public ListSection(Object[] objects, int startingPoint, int overAllSize)
  {
    this(Arrays.asList(objects), startingPoint, overAllSize);
  }
  /************************************************************************/
  public ListSection(Object[] objects, ListSectionInfo listSection)
  {
    this(objects, listSection.getStart() - 1, listSection.getTotalSize());
  }
  /************************************************************************/
  public ListSection(List<Object> section, ListSectionInfo listSection)
  {
    this(section, listSection.getStart() - 1, listSection.getTotalSize());
  }
  /************************************************************************/
  public void setSection(List<Object> section)
  {
    this.section = section;
  }
  /************************************************************************/
  public void setSection(Object[] objects)
  {
    this.section = Arrays.asList(objects);
  }
  /************************************************************************/
  public List<Object> getSection()
  {
    return this.section;
  }
  /************************************************************************/
  public boolean isListPoint()
  {
    return (section.size() == 1);
  }
  /************************************************************************/
  public int getSectionStartingPoint(int offset)
  {
    return (getSize() == 0) ? 0 : startingPoint + offset;
  }
  /************************************************************************/
  public int getSectionEndPoint(int offset)
  {
    // 1-2 of 3 = (0,1,2)
    // size = 2, starting point = 0,
    return startingPoint + section.size() - 1 + offset;
  }
  /************************************************************************/
  public boolean isBeforeSection()
  {
    return startingPoint > 0;
  }
  /************************************************************************/
  public boolean isAfterSection()
  {
    return getSectionEndPoint(1) != getSize();
  }
  /************************************************************************/
  public boolean isEmpty()
  {
    return section.size() == 0;
  }
  /************************************************************************/
  public int getSize()
  {
    return overAllSize;
  }
  /************************************************************************/
  public int getBeforeSectionSize(int max)
  {
    return (startingPoint > max || max == -1) ? max : startingPoint;
  }
  /************************************************************************/
  public int getBeforeSectionStartIndex(int max, int offset)
  {
    return startingPoint - getBeforeSectionSize(max) + offset;
  }
  /************************************************************************/
  public int getBeforeSectionEndIndex(int offset)
  {
    return startingPoint - 1 + offset;
  }
  /************************************************************************/
  public int getAfterSectionSize(int max)
  {
    int afterSize = getSize() - getSectionEndPoint(1);
    return (afterSize > max || max == -1) ? max : afterSize;
  }
  /************************************************************************/
  public int getAfterSectionStartIndex(int offset)
  {
    return getSectionEndPoint(1) + offset;
  }
  /************************************************************************/
  public int getAfterSectionEndIndex(int max, int offset)
  {
    return getSectionEndPoint(0) + getAfterSectionSize(max) + offset;
  }
  /************************************************************************/
  public static void main(String args[])
  {
    ArrayList<Object> v = new ArrayList<Object>();
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    v.add(null);
    ListSection l = new ListSection(v, 40, 63);
    int offset = 1;
    int max = 20;
    SimpleLogger.variable("isListPoint() = " + l.isListPoint());
    SimpleLogger.variable("getSectionStartingPoint() = " + l.getSectionStartingPoint(offset));
    SimpleLogger.variable("getSectionEndPoint() = " + l.getSectionEndPoint(offset));
    SimpleLogger.variable("isBeforeSection() = " + l.isBeforeSection());
    SimpleLogger.variable("isAfterSection() = " + l.isAfterSection());
    SimpleLogger.variable("getSize() = " + l.getSize());
    SimpleLogger.variable("getBeforeSectionSize() = " + l.getBeforeSectionSize(max));
    SimpleLogger.variable("getBeforeSectionStartIndex() = " + l.getBeforeSectionStartIndex(max, offset));
    SimpleLogger.variable("getBeforeSectionEndIndex() = " + l.getBeforeSectionEndIndex(offset));
    SimpleLogger.variable("getAfterSectionSize() = " + l.getAfterSectionSize(max));
    SimpleLogger.variable("getAfterSectionStartIndex() = " + l.getAfterSectionStartIndex(offset));
    SimpleLogger.variable("getAfterSectionEndIndex() = " + l.getAfterSectionEndIndex(max, offset));
  }
  /**
   * @return Returns the overAllSize.
   */
  public int getOverAllSize()
  {
    return overAllSize;
  }
  /**
   * @param overAllSize The overAllSize to set.
   */
  public void setOverAllSize(int overAllSize)
  {
    this.overAllSize = overAllSize;
  }
  /**
   * @return Returns the startingPoint.
   */
  public int getStartingPoint()
  {
    return startingPoint;
  }
  /**
   * @param startingPoint The startingPoint to set.
   */
  public void setStartingPoint(int startingPoint)
  {
    this.startingPoint = startingPoint;
  }
  /************************************************************************/
  /************************************************************************/
}