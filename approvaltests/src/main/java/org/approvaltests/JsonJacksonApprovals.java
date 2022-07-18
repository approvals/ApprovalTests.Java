package org.approvaltests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spun.util.ObjectUtils;
import org.approvaltests.core.Options;

public class JsonJacksonApprovals
{
  public static void verifyAsJson(Object o)
  {
    verifyAsJson(o, new Options());
  }
  public static void verifyAsJson(Object o, Options options)
  {
    Approvals.verify(asJson(o), options.forFile().withExtension(".json"));
  }
  public static String asJson(Object o)
  {
    try
    {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }
    catch (JsonProcessingException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
