package org.approvaltests.tests;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;
import org.junit.Test;

@UseReporter(DiffMergeReporter.class)
public class JsonFormattingTest
{
  @Test
  public void testBasicFormatting() throws Exception
  {
    String json = "{\"infos\":{\"address\":\"my address\",\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    Approvals.verifyJson(json);
  }
  @Test
  public void testIncorrectFormatting() throws Exception
  {
    String json = "{\"infos\":{address:my address,\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    Approvals.verifyJson(json);
  }
}
