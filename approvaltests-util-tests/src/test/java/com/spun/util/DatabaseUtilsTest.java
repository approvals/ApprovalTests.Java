package com.spun.util;

import java.sql.SQLException;
import junit.framework.TestCase;

public class DatabaseUtilsTest extends TestCase
{

  public void testTransaction() throws SQLException
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
