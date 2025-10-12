package org.approvaltests.namer;

import com.spun.util.ThreadUtils;
import com.spun.util.tests.TestUtils;
import net.jqwik.api.Example;
import net.jqwik.api.Property;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttributeStackSelectorTest
{
  @Property(tries = 1)
  void testDetectionOfAnnotationsThatAreMarkedTestable()
  {
    StackTraceElement element = ThreadUtils.getStackTrace()[2];
    assertTrue(AttributeStackSelector.isTestableMethod(element));
  }

  @Example()
  void testDetectionOfAnnotationsThatAreIndirectlyMarkedTestable()
  {
    StackTraceElement element = ThreadUtils.getStackTrace()[2];
    assertTrue(AttributeStackSelector.isTestableMethod(element));
  }

  @Test
  void unrollLambda()
  {
    String[] methodNames = {"doStuff", "lambda$handleCallback$0", "test_in_kotlin$lambda-1$lambda-0"};
    Approvals.verifyAll("unroll lambda", methodNames,
        m -> String.format("%s -> %s", m, TestUtils.unrollLambda(m)));
  }

  @RepeatedTest(10)
  public void selectElement()
  {
    callUnusedMethods();
    AttributeStackSelector selector = new AttributeStackSelector();
    StackTraceElement[] stackTrace = ThreadUtils.getStackTrace();
    assertEquals(stackTrace[2], selector.selectElement(stackTrace));
  }

  private void callUnusedMethods()
  {
    selectElement("");
    selectElement(0);
    selectElement((AttributeStackSelectorTest) null);
    selectElement((StackTraceElement) null);
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
