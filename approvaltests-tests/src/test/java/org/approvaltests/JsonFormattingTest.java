package org.approvaltests;

import com.google.gson.GsonBuilder;
import com.spun.util.JsonUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class JsonFormattingTest
{
  @Test
  public void testGsonCircular()
  {
    Approvals.verifyException(() -> {
      JsonApprovals.verifyAsJson(Circular.getIndirectCircularReference());
    });
  }
  @Test
  public void testBasicFormatting()
  {
    String json = "{\"infos\":{\"address\":\"my address\",\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    JsonApprovals.verifyJson(json);
  }
  @Test
  public void testIncorrectFormatting()
  {
    String json = "{\"infos\":{address:my address,\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    JsonApprovals.verifyJson(json);
  }
  @Test
  void demonstrateNullIssueInVerifyJson()
  {
    // begin-snippet: CustomGsonBuilderShowingNull
    Person person = new Person("Max", null, 1);
    JsonApprovals.verifyAsJson(person, GsonBuilder::serializeNulls);
    // end-snippet
  }
  static class Person
  {
    public String firstName;
    public String lastName;
    public int    age;
    public Person(String firstName, String lastName, int age)
    {
      this.firstName = firstName;
      this.lastName = lastName;
      this.age = age;
    }
  }
  @Test
  public void testInstantAndOtherDateObjects()
  {
    DateStuff dateStuff = new DateStuff();
    dateStuff.instant = Instant.ofEpochSecond(1651708441);
    JsonApprovals.verifyAsJson(dateStuff);
  }
  private static class DateStuff
  {
    public Instant instant;
  }
  @Test
  public void testJsonFieldOrdering()
  {
    Approvals.settings().allowMultipleVerifyCallsForThisMethod();
    String json1 = "{\"infos\":{\"address\":\"my address\",\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    String json2 = "{\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]},\"infos\":{\"phone\":\"my phone\",\"address\":\"my address\"}}";
    JsonApprovals.verifyJson(json1, true);
    JsonApprovals.verifyJson(json2, true);
    JsonApprovals.verifyJson(JsonUtils.reorderFields(json1));
    JsonApprovals.verifyJson(JsonUtils.reorderFields(json2));
  }
  @Test
  public void testJsonFieldOrderingWithNulls()
  {
    Approvals.settings().allowMultipleVerifyCallsForThisMethod();
    String json1 = "{\"infos\":{\"address\":null,\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    JsonApprovals.verifyJson(json1, true, g -> g.serializeNulls());
  }
}
