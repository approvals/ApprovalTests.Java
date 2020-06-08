package com.spun.util.tests;

import java.sql.SQLException;
import junit.framework.TestCase;
import com.spun.util.DatabaseUtils;

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
