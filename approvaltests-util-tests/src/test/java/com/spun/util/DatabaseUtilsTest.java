package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DatabaseUtilsTest
{
  @Test
  public void testTransaction() 
  {
    MockConnection connection1 = new MockConnection();
    MockConnection connection2 = new MockConnection();
    DatabaseUtils.beginTransaction(connection1);
    assertEquals(1, connection1.autoCommitCount);
    DatabaseUtils.beginTransaction(connection1);
    assertEquals(1, connection1.autoCommitCount);
    connection1 = null;
    System.gc();
    DatabaseUtils.beginTransaction(connection2);
    assertEquals(1, connection2.autoCommitCount);
    DatabaseUtils.commit(connection2);
    assertEquals(1, connection2.commitCount);
  }
}
