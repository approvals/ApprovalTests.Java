package com.spun.util;

import com.spun.util.date.DateRange;
import java.sql.Timestamp;
import org.approvaltests.Approvals;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class DepercatedTest
{
  @Test
  public void ThreadLauncher()
  {
    Approvals.verifyException(() -> new com.spun.util.ThreadLauncher("text", "length", 0));
  }
  @Test
  public void DateRange_getFilter()
  {
    Approvals.verifyException(() -> new DateRange(null, null).getFilter(Timestamp.class, "clone"));
  }
  @Test
  public void ObjectUtils_getForMethod()
  {
    Integer[] array = {1, 2, 3};
    Approvals.verifyException(() -> ObjectUtils.getForMethod(array, "2", "toString", "toString"));
  }
  @Test
  public void ObjectUtils_extractArray()
  {
    Integer[] array = {1, 2, 3};
    Approvals.verifyException(() -> ObjectUtils.extractArray(array, "toString"));
  }
  @Test
  public void MethodSorter()
  {
    Approvals.verifyException(() -> new com.spun.util.MethodSorter<>(String.class, "length", false));
  }
}
