package com.spun.util.tests;

import org.approvaltests.Approvals;
import org.junit.Test;

import com.spun.util.NumberUtils;

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
    Approvals
        .verifyException(() -> new com.spun.util.ThreadLauncher("text", "replaceAll", new Object[]{"A", "B"}, 0));
  }
}
