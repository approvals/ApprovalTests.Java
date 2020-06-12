package com.spun.util;

import java.sql.Timestamp;

import org.approvaltests.Approvals;
import org.junit.Test;

import com.spun.util.NumberUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.date.DateRange;

@SuppressWarnings("deprecation")
public class DepercatedTest
{
  @Test
  public void NumberUtils_sum() throws Exception
  {
    Approvals.verifyException(() -> NumberUtils.sum(new Integer[]{1, 2, 3}, "intValue"));
  }
  @Test
  public void ThreadLauncher() throws Exception
  {
    Approvals.verifyException(() -> new com.spun.util.ThreadLauncher("text", "length", 0));
  }
  @Test
  public void DateRange_getFilter() throws Exception
  {
    Approvals.verifyException(() -> new DateRange(null, null).getFilter(Timestamp.class, "clone"));
  }
  @Test
  public void ObjectUtils_getForMethod() throws Exception
  {
    Integer[] array = {1, 2, 3};
    Approvals.verifyException(() -> ObjectUtils.getForMethod(array, "2", "toString", "toString"));
  }
  @Test
  public void ObjectUtils_extractArray() throws Exception
  {
    Integer[] array = {1, 2, 3};
    Approvals.verifyException(() -> ObjectUtils.extractArray(array, "toString"));
  }
  @Test
  public void MethodSorter() throws Exception
  {
    Approvals.verifyException(() -> new com.spun.util.MethodSorter<>(String.class, "length", false));
  }
}
