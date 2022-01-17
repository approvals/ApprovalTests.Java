package org.approvaltests;

import com.google.gson.GsonBuilder;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;
import org.junit.jupiter.api.Test;

@UseReporter(DiffMergeReporter.class)
public class JsonFormattingTest {

  @Test
  public void testBasicFormatting() {
    String json = "{\"infos\":{\"address\":\"my address\",\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    Approvals.verifyJson(json);
  }

  @Test
  public void testIncorrectFormatting() {
    String json = "{\"infos\":{address:my address,\"phone\":\"my phone\"},\"insurance\":{\"forks\":[14,53,123],\"prices\":[5,8,\"3%\"]}}";
    Approvals.verifyJson(json);
  }

  @Test
  void demonstrateNullIssueInVerifyJson() {
    Person person = new Person("Max", null, 1);
    Approvals.verifyAsJson(person, GsonBuilder::serializeNulls, GsonBuilder.class);
  }

  static class Person {

    public String firstName;
    public String lastName;
    public int age;

    public Person(String firstName, String lastName, int age) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.age = age;
    }
  }
}
