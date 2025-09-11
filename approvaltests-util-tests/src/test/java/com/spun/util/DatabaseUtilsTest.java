package com.spun.util;

import com.spun.util.logger.SimpleLogger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseUtilsTest
{
  @Test
  public void testTransaction()
  {
    try (var __ = SimpleLogger.quiet())
    {
      MockConnection connection1 = new MockConnection();
      MockConnection connection2 = new MockConnection();
      DatabaseUtils.beginTransaction(connection1);
      assertEquals(1, connection1.autoCommitCount);
      DatabaseUtils.beginTransaction(connection1);
      assertEquals(1, connection1.autoCommitCount);
      connection1 = null; // So that GC can run
      System.gc();
      DatabaseUtils.beginTransaction(connection2);
      assertEquals(1, connection2.autoCommitCount);
      DatabaseUtils.commit(connection2);
      assertEquals(1, connection2.commitCount);
    }
  }
}
