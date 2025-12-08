package org.approvaltests;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import tools.jackson.databind.SerializationFeature;

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
  void demonstrateJsonJacksonApprovalsIssues()
  {
    // Create an object with:
    // 1. Java 8 time types (will fail serialization or produce ugly output)
    // 2. Multiple properties (to show non-deterministic property order)
    // 3. A Map field (to show non-deterministic map entry order)
    class Event
    {
      public String              name      = "Conference";
      public LocalDateTime       startTime = LocalDateTime.of(2024, 3, 15, 9, 0);
      public LocalDate           date      = LocalDate.of(2024, 3, 15);
      public Instant             createdAt = Instant.parse("2024-01-01T00:00:00Z");
      public Map<String, String> metadata  = new LinkedHashMap<>();
      public String              location  = "Berlin";
      public int                 capacity  = 500;
      {
        metadata.put("zebra", "last");
        metadata.put("apple", "first");
        metadata.put("mango", "middle");
      }
    }
    JsonJackson3Approvals.verifyAsJson(new Event(),
        om -> om.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS));
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
