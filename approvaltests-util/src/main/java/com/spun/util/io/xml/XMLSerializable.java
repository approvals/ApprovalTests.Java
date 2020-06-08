package com.spun.util.io.xml;

import com.spun.util.database.DatabaseObject;

public interface XMLSerializable
{

  /**
   * Serializes this object , plus dependencies to xml.
   * @return the xml
   **/
  public String serializeXML();

  /**
   * Serializes this object , plus dependencies to xml via the writer.
   * @return writer that was used
   **/
  public java.io.Writer serializeXML(java.io.Writer writer);

  /**
   * Serializes this object , plus dependencies to xml via the writer.
   **/
  public void serializeXML(java.util.HashMap<DatabaseObject, Integer> saved, java.io.Writer writer) throws java.io.IOException;
}