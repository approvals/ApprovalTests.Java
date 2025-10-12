package com.spun.util.database;

import com.spun.util.ObjectUtils;

public class CircularRedundancyError extends Error
{
  private static final long serialVersionUID = 1L;
  private DatabaseObject    source;
  private String            causedBy;
  private String            xmlState;
  public CircularRedundancyError(DatabaseObject source, String causedBy, String xmlState)
  {
    this.source = source;
    this.causedBy = causedBy;
    this.xmlState = xmlState;
  }

  public String getMessage()
  {
    return "Circular Redundancy occurred while saving :" + ObjectUtils.getClassName(source) + "\n" + "cause by :"
        + causedBy + "\n" + "state : " + xmlState;
  }

  public String getCausedBy()
  {
    return causedBy;
  }

  public DatabaseObject getSource()
  {
    return source;
  }

  public String getXmlState()
  {
    return xmlState;
  }
}
