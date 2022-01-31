package org.approvaltests.namer;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class NamerFactoryTest
{
  @Test
  public void testMultipleFiles()
  {
    try (MultipleFilesLabeller labeller = NamerFactory.useMultipleFiles())
    {
      Approvals.verify("one");
      labeller.next();
      Approvals.verify("two");
    }
  }
  @ParameterizedTest
  @CsvSource({"Oskar,4", "Birgit,1"})
  void testNamingWithMultipleParameters(String name, int age)
  {
    try (NamedEnvironment en = NamerFactory.withParameters(name, age))
    {
      Object output = name + ":" + age;
      Approvals.verify(output);
    }
  }
}
