package com.spun.util.database.automaticsetter;

import java.sql.Timestamp;
import java.util.Date;

import com.spun.util.ObjectUtils;
import com.spun.util.filters.Filter;

public interface AddDateAware
{

  public boolean setAddDate(Timestamp time);

  public Timestamp getAddDate();


  public static class FilterBeforeAddDate implements Filter<AddDateAware>
  {
    private Date date = null;

    public FilterBeforeAddDate(Date date)
    {
      this.date = date;
    }

    public boolean isExtracted(AddDateAware object) throws IllegalArgumentException
    {
      ObjectUtils.assertInstance(AddDateAware.class, object);
      Timestamp addDate = ((AddDateAware) object).getAddDate();
      return (addDate == null) ? false : !addDate.after(date);
    }
  }
}