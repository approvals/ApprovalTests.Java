package com.spun.util.database;

import com.spun.util.DatabaseUtils;

public class ColumnMetadata
{
  private Metadata       tableMetadata;
  private String         name;
  private Class<?>       type;
  private int            length;
  private boolean        nullable;
  private int            sqlType;
  private int            decimalDigits;
  private String         comments;
  private int            columnPosition;
  private String         foreignTable;
  private ColumnMetadata foreignColumn;
  public ColumnMetadata(Metadata tableMetadata, String name, Class<?> type, int length, int columnPosition,
      boolean nullable, int sqlType, int decimalDigits, String comments)
  {
    this(tableMetadata, name, type, length, columnPosition, nullable, sqlType, decimalDigits, comments, null,
        null);
  }
  public ColumnMetadata(Metadata tableMetadata, String name, Class<?> type, int length, int columnPosition,
      boolean nullable, int sqlType, int decimalDigits, String comments, String foreignTable,
      ColumnMetadata foreignColumn)
  {
    this.columnPosition = columnPosition;
    this.tableMetadata = tableMetadata;
    this.name = name;
    this.type = type;
    this.length = length;
    this.nullable = nullable;
    this.sqlType = sqlType;
    this.decimalDigits = decimalDigits;
    this.comments = comments;
    this.foreignTable = foreignTable;
    this.foreignColumn = foreignColumn;
  }
  public int getLength()
  {
    return length;
  }
  public int getSqlType()
  {
    return sqlType;
  }
  public String getSqlTypeName()
  {
    String name = DatabaseUtils.findDatabaseName(sqlType);
    if (name == null)
      name = "unknown: " + getType();
    else if (name == "decimal" || name.startsWith("numeric"))
    {
      name += "(" + length + ", " + decimalDigits + ")";
    }
    else if (name == "varchar" || name == "char")
    {
      name += "(" + length + ")";
    }
    return name;
  }
  public int getColumnPosition()
  {
    return columnPosition;
  }
  public String getName()
  {
    return name;
  }
  public boolean isNullable()
  {
    return nullable;
  }
  public Class<?> getType()
  {
    return type;
  }
  public int getDecimalDigits()
  {
    return decimalDigits;
  }
  public String getComments()
  {
    return comments;
  }
  public String getForeignTable()
  {
    return foreignTable;
  }
  public ColumnMetadata getForeignColumn()
  {
    return foreignColumn;
  }
  public String getNameWithPrefix(String alias)
  {
    return alias + "." + getName();
  }
  public String getSequenceName(int databaseType)
  {
    if (databaseType == DatabaseUtils.POSTGRESQL)
    { return String.format("%s_%s_seq", tableMetadata.getTableName(), this.getName()); }
    return null;
  }
  /**
   * A convenience function to turn a vector of com.spun.util.database.ColumnMetadata objects
   * into an Array of the com.spun.util.database.ColumnMetadata objects.
   * @param vectorOf a Vector of com.spun.util.database.ColumnMetadata objects
   * @return the array of com.spun.util.database.ColumnMetadata.
   * @throws Error if an element of vectorOf is not a com.spun.util.database.ColumnMetadata object.
   **/
  public static com.spun.util.database.ColumnMetadata[] toArray(java.util.Collection<ColumnMetadata> vectorOf)
  {
    if (vectorOf == null)
    { return new com.spun.util.database.ColumnMetadata[0]; }
    com.spun.util.database.ColumnMetadata array[] = new com.spun.util.database.ColumnMetadata[vectorOf.size()];
    java.util.Iterator<ColumnMetadata> iterator = vectorOf.iterator();
    int i = 0;
    while (iterator.hasNext())
    {
      java.lang.Object rowObject = iterator.next();
      if (rowObject instanceof com.spun.util.database.ColumnMetadata)
      {
        array[i++] = (com.spun.util.database.ColumnMetadata) rowObject;
      }
      else
      {
        throw new Error("toArray[" + i + "] is not an instance of com.spun.util.database.ColumnMetadata but a "
            + rowObject.getClass().getName());
      }
    }
    return array;
  }
}