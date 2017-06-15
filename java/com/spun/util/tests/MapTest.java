package com.spun.util.tests;

import java.util.Date;
import java.util.Map;

import org.approvaltests.Approvals;
import org.junit.Test;

import com.spun.util.MapBuilder;

public class MapTest
{
  @Test
  public void test()
  {
    Map p = params("name", "Llewellyn").and("age", 40).and("date", new Date(2001, 01, 01));
    Approvals.verify(p);
  }
  public static MapBuilder<String, Object> params(String key, Object value)
  {
    return new MapBuilder<String, Object>(key, value);
  }
}
