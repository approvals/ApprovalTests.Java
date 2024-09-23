package com.spun.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.lambda.functions.Function1;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class JsonUtils
{
  public static String prettyPrint(String json)
  {
    return prettyPrint(json, g -> g);
  }
  public static String prettyPrint(String json, Function1<GsonBuilder, GsonBuilder> gsonBuilder)
  {
    if (!ObjectUtils.isClassPresent("com.google.gson.Gson"))
    {
      throw new RuntimeException(
          "Missing Gson dependency\n  Pretty print uses Gson parser.\n  You can get this from the maven repo \n  or https://github.com/google/gson");
    }
    try
    {
      Gson gson = gsonBuilder.call(new GsonBuilder()).setPrettyPrinting().create();
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
    try
    {
      GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
      builder = addHandlingForDateObjects(builder);
      Gson gson = gsonBuilder.call(builder).create();
      return gson.toJson(o);
    }
    catch (StackOverflowError e)
    {
      throw new RuntimeException(
          "Circular reference found.\nGson does not handle circular references.\nConsider:\n  1. Using XStream (JsonXstreamApprovals)\n  2. Remove the circular reference.",
          e);
    }
  }
  private static GsonBuilder addHandlingForDateObjects(GsonBuilder builder)
  {
    builder = builder.registerTypeAdapter(Instant.class, new InstantAdapter());
    builder = builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    return builder;
  }
  public static String reorderFields(String json)
  {
    return reorderFields(json, g -> g);
  }
  public static String reorderFields(String json, Function1<GsonBuilder, GsonBuilder> gsonBuilder)
  {
    JsonObject sortedJsonObject = sortJsonObject(json);
    return asJson(sortedJsonObject, gsonBuilder);
  }
  public static JsonObject sortJsonObject(String json)
  {
    JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
    return sortJsonObjectFields(jsonObject);
  }
  public static JsonObject sortJsonObjectFields(JsonObject jsonObject)
  {
    JsonObject sortedJsonObject = new JsonObject();
    Map<String, JsonElement> sortedFirstLevelFields = jsonObject.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, TreeMap::new));
    for (Map.Entry<String, JsonElement> entry : sortedFirstLevelFields.entrySet())
    {
      JsonElement element = entry.getValue();
      if (element.isJsonObject())
      {
        sortedJsonObject.add(entry.getKey(), sortJsonObjectFields(element.getAsJsonObject()));
      }
      else
      {
        sortedJsonObject.add(entry.getKey(), element);
      }
    }
    return sortedJsonObject;
  }
  public static class InstantAdapter extends TypeAdapter<Instant>
  {
    @Override
    public void write(JsonWriter jsonWriter, Instant instant) throws IOException
    {
      jsonWriter.value(instant.toString());
    }
    @Override
    public Instant read(JsonReader jsonReader) throws IOException
    {
      throw new IOException("Never called");
    }
  }
  public static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime>
  {
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime instant) throws IOException
    {
      if (instant == null)
      {
        jsonWriter.nullValue();
      }
      else
      {
        jsonWriter.value("" + instant);
      }
    }
    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException
    {
      throw new IOException("Never called");
    }
  }
}
