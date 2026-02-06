package org.approvaltests;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.time.Instant;
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
  @Test
  void nullInstantTest()
  {
    InstantWrapper instantWrapper = new InstantWrapper();
    JsonApprovals.verifyAsJson(instantWrapper, g -> g.serializeNulls());
  }
  private class InstantWrapper
  {
    public Instant getInstant()
    {
      return instant;
    }

    public void setInstant(Instant instant)
    {
      this.instant = instant;
    }
    private Instant instant;
  }
  @Test
  void verifyJsonReorderWithoutArray()
  {
    Approvals.settings().allowMultipleVerifyCallsForThisMethod();
    String variant1 = """
        	{
        		"a" = 1,
        		"b" = 2
        	}
        """;
    String variant2 = """
        	{
        		"b" = 2,
        		"a" = 1
        	}
        """;
    // It does not matter on which we call the verifyJson
    JsonApprovals.verifyJson(variant1, true);
    JsonApprovals.verifyJson(variant2, true);
  }

  @Test
  void verifyJsonReorderWithArray()
  {
    Approvals.settings().allowMultipleVerifyCallsForThisMethod();
    String variant1 = """
        	{
        		"array" = [
        			{
        				"a" = 1,
        				"b" = 2
        			}
        		]
        	}
        """;
    String variant2 = """
        	{
        		"array" = [
        			{
        				"b" = 2,
        				"a" = 1
        			}
        		]
        	}
        """;
    // Now both variants should work since objects within arrays get reordered
    JsonApprovals.verifyJson(variant1, true);
    JsonApprovals.verifyJson(variant2, true);
  }
}
