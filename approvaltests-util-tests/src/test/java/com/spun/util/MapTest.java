package com.spun.util;

import java.util.Map;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class MapTest
{
  @Test
  public void test()
  {
    Map<String, Object> p = params("name", "Llewellyn").and("age", 40).and("date", DateUtils.parse("2001/01/01"));
    Approvals.verify(p);
  }
  public static MapBuilder<String, Object> params(String key, Object value)
  {
    return new MapBuilder<>(key, value);
  }
}
