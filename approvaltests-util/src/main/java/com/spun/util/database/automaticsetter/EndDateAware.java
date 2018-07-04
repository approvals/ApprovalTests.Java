package com.spun.util.database.automaticsetter;

import java.sql.Timestamp;
import java.util.Date;

public interface EndDateAware extends AddDateAware
{
  public String getEffectivityKey();
  public boolean setEndDate(Timestamp time);
  public Timestamp getEndDate();
  /***********************************************************************/
  /*                        INNER CLASSES                                */
  /***********************************************************************/
  public static class Utils
  {
    public static boolean isValid(EndDateAware object, Date time)
    {
      return (object.getAddDate().before(time)
          && (object.getEndDate() == null || !object.getEndDate().before(time)));
    }
  }
}