package com.spun.util.database.management;

public class ColumnDiscrepancy
{
  private String columnName;
  private String discrepancyType;
  private String databaseValue;
  private String metadataValue;

  /************************************************************************/
  public ColumnDiscrepancy(String columnName, String discrepancyType,
      String databaseValue, String metadataValue)
  {
    this.columnName = columnName;
    this.discrepancyType = discrepancyType;
    this.databaseValue = databaseValue;
    this.metadataValue = metadataValue;
  }

  /***********************************************************************/
  public static ColumnDiscrepancy[] toArray(
      java.util.Collection<ColumnDiscrepancy> vectorOf)
  {
    if (vectorOf == null)
    {
      return new ColumnDiscrepancy[0];
    }
    ColumnDiscrepancy array[] = new ColumnDiscrepancy[vectorOf.size()];
    java.util.Iterator iterator = vectorOf.iterator();
    int i = 0;
    while (iterator.hasNext())
    {
      java.lang.Object rowObject = iterator.next();
      if (rowObject instanceof ColumnDiscrepancy)
      {
        array[i++] = (ColumnDiscrepancy) rowObject;
      } else
      {
        throw new Error("toArray[" + i
            + "] is not an instance of ColumnDiscrepancy but a "
            + rowObject.getClass().getName());
      }
    }
    return array;
  }

  /************************************************************************/
  public String getColumnName()
  {
    return columnName;
  }

  /************************************************************************/
  public String getDatabaseValue()
  {
    return databaseValue;
  }

  /************************************************************************/
  public String getDiscrepancyType()
  {
    return discrepancyType;
  }

  /************************************************************************/
  public String getMetadataValue()
  {
    return metadataValue;
  }

  /***********************************************************************/
  public String toString()
  {
    return this.discrepancyType + " discrepancy on column '" + this.columnName
        + "'  Metadata expects '" + this.metadataValue
        + "'  Database returned '" + this.databaseValue + "'";
  }
  /************************************************************************/
  /************************************************************************/
}