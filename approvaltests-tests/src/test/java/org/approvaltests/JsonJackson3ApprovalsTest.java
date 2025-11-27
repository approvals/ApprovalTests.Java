package org.approvaltests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.junit.jupiter.api.Test;

class JsonJackson3ApprovalsTest
{
  @Test
  void testObjectMapperOverride()
  {
    MyClass o = new MyClass();
    JsonJackson3Approvals.verifyAsJson(o,
        jm -> jm.changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL)));
  }

  @Test
  void testDuplicateFields()
  {
    JsonJackson3Approvals.verifyAsJson(new MyOtherClass());
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
    @Override
    public String getName()
    {
      return name;
    }

    @Override
    public void setName(String name)
    {
      this.name = name;
    }
  }
}
