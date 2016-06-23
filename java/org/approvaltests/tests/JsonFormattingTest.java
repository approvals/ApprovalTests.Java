package org.approvaltests.tests;

import org.approvaltests.Approvals;
import org.junit.Test;

public class JsonFormattingTest
{
  @Test
  public void testBasicFormatting() throws Exception
  {
    String json = "{\"infos\":{\"address\":\"my address\",\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    Approvals.verifyJson(json);
  }
}
