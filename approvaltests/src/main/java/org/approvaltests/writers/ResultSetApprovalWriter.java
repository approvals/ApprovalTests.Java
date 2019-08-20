package org.approvaltests.writers;

import java.io.File;
import java.sql.ResultSet;

import org.approvaltests.core.ApprovalWriter;

import com.spun.util.database.ResultSetWriter;
import com.spun.util.io.FileUtils;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.ContextAware.ContextAwareMap;
import com.spun.util.velocity.VelocityParser;

public class ResultSetApprovalWriter implements ApprovalWriter
{
  private final ResultSet resultSet;
  public ResultSetApprovalWriter(ResultSet resultSet)
  {
    this.resultSet = resultSet;
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return base + Writer.approved + ".csv";
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return base + Writer.received + ".csv";
  }
  @Override
  public String writeReceivedFile(String received)
  {
    String template = "#foreach ($row in $commons.asArray($metaData))$row.get()#if (!$row.isLast()),#end#end\n"
        + "\n" + "#foreach ($row in $results)\n"
        + "#foreach ($column in $commons.asArray($row))$commons.asExcel($column.get())#if (!$column.isLast()),#end#end \n"
        + "\n" + "#end ";
    ContextAwareMap map = new ContextAware.ContextAwareMap("metaData", ResultSetWriter.extractMetaData(resultSet));
    map.put("results", ResultSetWriter.extractResults(resultSet));
    String output = VelocityParser.parseString(template, map);
    FileUtils.writeFile(new File(received), output);
    return received;
  }
}
