package com.spun.util.database.automaticsetter;

import java.sql.Timestamp;
import java.util.Date;
import com.spun.util.ObjectUtils;

public interface EndDateAware extends AddDateAware
{
  /***********************************************************************/
  public String getEffectivityKey();
  /***********************************************************************/
  public boolean setEndDate(Timestamp time);
  /***********************************************************************/
  public Timestamp getEndDate();
  /***********************************************************************/
  /*                        INNER CLASSES                                */
  /***********************************************************************/
  public static class Utils
  {
    public static boolean isValid(EndDateAware object, Date time)
    {
      return (object.getAddDate().before(time) && (object.getEndDate() == null || !object.getEndDate().before(time)));
    }
  }
  /************************************************************************/
  public static class FilterValidAtTime implements com.spun.util.filters.Filter
  {
    private Date time;
    /***********************************************************************/
    public FilterValidAtTime(Date time)
    {
      super();
      this.time = time;
    }
    /***********************************************************************/
      public boolean isExtracted(Object object) throws IllegalArgumentException
    {
      ObjectUtils.assertInstance(EndDateAware.class, object);
      return Utils.isValid((EndDateAware) object, time);
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}