package org.approvaltests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spun.util.ObjectUtils;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;

import java.util.function.Function;

public class JsonJacksonApprovals
{
  public static void verifyAsJson(Object o)
  {
    verifyAsJson(o, new Options());
  }
  public static void verifyAsJson(Object o, Options options)
  {
    verifyAsJson(o, a -> a, options);
  }
  public static void verifyAsJson(Object o, Function1<ObjectMapper, ObjectMapper> objectMapperBuilder)
  {
    verifyAsJson(o, objectMapperBuilder, new Options());
  }
  public static void verifyAsJson(Object o, Function1<ObjectMapper, ObjectMapper> objectMapperBuilder, Options options)
  {
    Approvals.verify(asJson(o, objectMapperBuilder), options.forFile().withExtension(".json"));
  }
  public static String asJson(Object o) {
    return asJson(o, a -> a);
  }
  public static String asJson(Object o, Function1<ObjectMapper, ObjectMapper> objectMapperBuilder)
  {
    try
    {
      ObjectMapper objectMapper = objectMapperBuilder.call(new ObjectMapper());
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }
    catch (JsonProcessingException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
