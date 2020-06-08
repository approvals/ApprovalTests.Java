package com.spun.util.database;


public interface Metadata
{

  /************************************************************************/
  public ColumnMetadata getColumnByName(String columnName);
  /************************************************************************/
  public String getTableName();
  /************************************************************************/
  public ColumnMetadata[] getColumns();
  /************************************************************************/  
  public String[] getColumnNames();
  /************************************************************************/
  
  
}
