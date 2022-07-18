package org.approvaltests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.junit.jupiter.api.Test;

public class JsonJacksonApprovalsTest
{
  @Test
  void testDuplicateFields()
  {
    JsonJacksonApprovals.verifyAsJson(new MyOtherClass());
  }
  private static class MyClass
  {
    @JsonIgnore
    private String name     = "MyClass";
    public String  lastName = "MyClass";
    public String getName()
    {
      return name;
    }
    public void setName(String name)
    {
      this.name = name;
    }
  }
  private static class MyOtherClass extends MyClass
  {
    @JsonIgnore
    private String name = "MyOtherClass";
    public String getName()
    {
      return name;
    }
    public void setName(String name)
    {
      this.name = name;
    }
  }
}
