package org.approvaltests.namer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class JUnit4ParameterizedNamerTest
{
  @Parameters
  public static Collection<Object[]> data()
  {
    return Arrays.asList(new Object[][]{{"A"}, {"B"}});
  }
  private String input;
  public JUnit4ParameterizedNamerTest(String input)
  {
    this.input = input;
  }

  @Test
  public void parameterizedTest()
  {
    StackTraceNamerUtils.assertParameterizedTest(getClass().getSimpleName(), "parameterizedTest", input);
  }
}
