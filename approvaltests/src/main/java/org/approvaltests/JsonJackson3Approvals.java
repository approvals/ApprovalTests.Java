package org.approvaltests;

import com.spun.util.ObjectUtils;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public class JsonJackson3Approvals
{
  private JsonJackson3Approvals()
  {
  }

  public static void verifyAsJson(Object o)
  {
    verifyAsJson(o, new Options());
  }

  public static void verifyAsJson(Object o, Options options)
  {
    verifyAsJson(o, a -> a, options);
  }

  public static void verifyAsJson(Object o, Function1<JsonMapper.Builder, JsonMapper.Builder> objectMapperBuilder)
  {
    verifyAsJson(o, objectMapperBuilder, new Options());
  }

  public static void verifyAsJson(Object o, Function1<JsonMapper.Builder, JsonMapper.Builder> objectMapperBuilder,
      Options options)
  {
    Approvals.verify(asJson(o, objectMapperBuilder), options.forFile().withExtension(".json"));
  }

  public static String asJson(Object o)
  {
    return asJson(o, a -> a);
  }

  public static String asJson(Object o, Function1<JsonMapper.Builder, JsonMapper.Builder> objectMapperBuilder)
  {
    try
    {
      ObjectMapper objectMapper = objectMapperBuilder.call(JsonMapper.builder()).build();
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }
    catch (JacksonException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
