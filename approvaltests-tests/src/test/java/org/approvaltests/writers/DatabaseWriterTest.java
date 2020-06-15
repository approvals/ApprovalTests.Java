package org.approvaltests.writers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.approvaltests.Approvals;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import com.spun.util.io.CommaDelimitedFileParser;
import com.spun.util.io.FileUtils;

public class DatabaseWriterTest
{
  @Test
  public void testSimpleQuery() throws Exception
  {
    ResultSet rs = mockResultSetFromFile("query.csv");
    Approvals.verify(rs);
  }
  @Test
  public void testSimpleQuery2() throws Exception
  {
    ResultSet rs = queryDatabase();
    Approvals.verify(rs);
  }
  public ResultSet queryDatabase() throws SQLException
  {
    return mockResultSetFromFile("sample_result_set.csv");
  }
  private ResultSet mockResultSetFromFile(String fileName) throws SQLException
  {
    String resultSet = FileUtils.readFromClassPath(getClass(), fileName).trim();
    String[][] data = CommaDelimitedFileParser.parse(resultSet);
    return mockResultSet(data);
  }
  private ResultSet mockResultSet(String[][] data) throws SQLException
  {
    ResultSet rs = EasyMock.createMock(ResultSet.class);
    ResultSetMetaData metaData = EasyMock.createMock(ResultSetMetaData.class);
    EasyMock.expect(rs.getMetaData()).andReturn(metaData).anyTimes();
    EasyMock.expect(metaData.getColumnCount()).andReturn(data[0].length).anyTimes();
    for (int i = 0; i < data[0].length; i++)
    {
      EasyMock.expect(metaData.getColumnName(i + 1)).andReturn(data[0][i]).anyTimes();
    }
    for (int i = 1; i < data.length; i++)
    {
      EasyMock.expect(rs.next()).andReturn(true);
      for (int j = 0; j < data[i].length; j++)
      {
        EasyMock.expect(rs.getString(j + 1)).andReturn(data[i][j]);
      }
    }
    EasyMock.expect(rs.next()).andReturn(false);
    EasyMock.replay(rs);
    EasyMock.replay(metaData);
    return rs;
  }
}
