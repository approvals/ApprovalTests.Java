package com.spun.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.lambda.functions.Function1;

public class JsonUtils
{
  public static String prettyPrint(String json)
  {
    if (!ObjectUtils.isClassPresent("com.google.gson.Gson"))
    {
      throw new RuntimeException(
          "Missing Gson dependency\n  Pretty print uses Gson parser.\n  You can get this from the maven repo \n  or https://github.com/google/gson");
    }
    try
    {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      JsonElement je = JsonParser.parseString(json);
      return gson.toJson(je);
    }
    catch (JsonSyntaxException e)
    {
      return String.format("Error:%s\nJson:\n%s", e.getMessage(), json);
    }
  }
  public static String asJson(Object o)
  {
    return asJson(o, (b) -> b);
  }
  public static <T> String asJsonWithBuilder(Object o, Function1<T, T> gsonBuilder, Class<T> gsonBuilderClass)
  {
    if (!ObjectUtils.isThisInstanceOfThat(gsonBuilderClass, com.google.gson.GsonBuilder.class))
    { throw new FormattedException("Class must be of type %s", GsonBuilder.class.getName()); }
    return asJson(o, (Function1<GsonBuilder, GsonBuilder>) gsonBuilder);
  }
  public static <T> String asJson(Object o, Function1<GsonBuilder, GsonBuilder> gsonBuilder)
  {
    Gson gson = gsonBuilder.call(new GsonBuilder().setPrettyPrinting()).create();
    return gson.toJson(o);
  }
}
