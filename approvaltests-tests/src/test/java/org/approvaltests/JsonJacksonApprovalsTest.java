package org.approvaltests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

public class JsonJacksonApprovalsTest
{
  @Test
  void testObjectMapperOverride()
  {
    MyClass o = new MyClass();
    JsonJacksonApprovals.verifyAsJson(o, (om) -> om.setSerializationInclusion(JsonInclude.Include.NON_NULL));
  }

  @Test
  void testDuplicateFields()
  {
    JsonJacksonApprovals.verifyAsJson(new MyOtherClass());
  }
  private static class MyClass
  {
    @JsonIgnore
    private String name       = "MyClass";
    public String  lastName   = "MyClass";
    public String  middleName = null;
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
