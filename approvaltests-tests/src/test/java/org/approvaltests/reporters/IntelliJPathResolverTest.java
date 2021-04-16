package org.approvaltests.reporters;

import org.approvaltests.reporters.intellij.Edition;
import org.approvaltests.reporters.intellij.IntelliJPathResolver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntelliJPathResolverTest
{
  @Test
  @Disabled
  void testFindsPath()
  {
    IntelliJPathResolver intelliJPathResolver = new IntelliJPathResolver(Edition.Community);
    String result = intelliJPathResolver.findIt();
    assertEquals(
        "C:\\Users\\Administrator\\AppData\\Local\\JetBrains\\Toolbox\\apps\\IDEA-C\\ch-0\\211.6693.111\\bin\\idea64.exe",
        result);
  }
}