package org.approvaltests;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function1;

import java.time.LocalDateTime;

public class JsonApprovalsTest
{
  @Test
  void nullDateTest()
  {
    LocalDateWrapper localDateWrapper = new LocalDateWrapper();
    JsonApprovals.verifyAsJson(localDateWrapper, g -> g.serializeNulls());
  }
  @Test
  void testUsingGsonBuilderWithPrettyPrint()
  {
    String jsonString = """
        {
          "name": "value",
          "localDate": null
        }
        """;
    JsonApprovals.verifyJson(jsonString, false, GsonBuilder::serializeNulls);
  }
  private class LocalDateWrapper
  {
    public LocalDateTime getLocalDate()
    {
      return localDate;
    }
    public void setLocalDate(LocalDateTime localDate)
    {
      this.localDate = localDate;
    }
    private LocalDateTime localDate;
  }
}
