package tests;

import org.approvaltests.namer.StackTraceNamer;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StackTraceNamerTest
{
  @Test
  void testNameCollision()
  {
    StackTraceNamer approvalNamer = new StackTraceNamer();
    String actual = approvalNamer.getApprovedFile(".txt").toString();
    assertTrue(actual.contains(File.separator + "src" + File.separator),
        "actual " + actual + " does not contain /src/");
  }
}
