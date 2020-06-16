package org.approvaltests;

import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;
import org.junit.jupiter.api.Test;

@UseReporter(DiffMergeReporter.class)
public class JsonFormattingTest
{
  @Test
  public void testBasicFormatting()
  {
    String json = "{\"infos\":{\"address\":\"my address\",\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    Approvals.verifyJson(json);
  }
  @Test
  public void testIncorrectFormatting()
  {
    String json = "{\"infos\":{address:my address,\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    Approvals.verifyJson(json);
  }
}
