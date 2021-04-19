package org.approvaltests.namer;

import com.spun.util.ThreadUtils;
import org.junit.Assert;
import org.junit.Test;

public class AttributeStackSelectorTest
{
  @Test
  public void selectElement() throws Exception
  {
    AttributeStackSelector selector = new AttributeStackSelector();
    StackTraceElement[] stackTrace = ThreadUtils.getStackTrace();
    Assert.assertEquals(stackTrace[2], selector.selectElement(stackTrace));
  }
  // these methods are overloads of the test method
  // to simulate flaky failures
  public void selectElement(String unused)
  {
  }
  public void selectElement(Integer unused)
  {
  }
  public void selectElement(AttributeStackSelectorTest unused)
  {
  }
  public void selectElement(StackTraceElement unused)
  {
  }
}
