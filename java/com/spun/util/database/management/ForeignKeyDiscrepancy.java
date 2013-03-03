package com.spun.util.database.management;

public class ForeignKeyDiscrepancy
{
  public enum Type {
    MISSING
  }
  private String onColumn;
  private Type   type;
  private String foreignTable;
  private String foreignColumn;  
  public ForeignKeyDiscrepancy(String onColumn, Type type, String foreignTable, String foreignColumn)
  {
    this.onColumn = onColumn;
    this.type = type;
    this.foreignTable = foreignTable;
    this.foreignColumn = foreignColumn;
  }
  public boolean isMissing()
  {
    return type == Type.MISSING;
  }
  public String getOnColumn()
  {
    return onColumn;
  }
  public Type getType()
  {
    return type;
  }
  public String getForeignTable()
  {
    return foreignTable;
  }
  public String getForeignColumn()
  {
    return foreignColumn;
  }
  public static ForeignKeyDiscrepancy[] toArray(java.util.Collection<ForeignKeyDiscrepancy> collection)
  {
    if (collection == null) { return new ForeignKeyDiscrepancy[0]; }
    return (ForeignKeyDiscrepancy[]) collection.toArray(new ForeignKeyDiscrepancy[collection.size()]);
  }
}
