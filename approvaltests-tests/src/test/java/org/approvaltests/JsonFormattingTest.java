package org.approvaltests;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

public class JsonFormattingTest
{
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
  public void testInstantAndOtherDateObjects() {
    DateStuff dateStuff = new DateStuff();
    dateStuff.instant = Instant.ofEpochSecond(1651708441);

    JsonApprovals.verifyAsJson(dateStuff);
  }
  private static class DateStuff {
    public Instant instant;
  }
}
