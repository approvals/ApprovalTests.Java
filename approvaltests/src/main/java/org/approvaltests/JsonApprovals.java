package org.approvaltests;

import com.google.gson.GsonBuilder;
import com.spun.util.JsonUtils;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;

/**
 * Requires (GSON)[https://mvnrepository.com/artifact/com.google.code.gson/gson]
 */
public class JsonApprovals
{
  public static void verifyJson(String json)
  {
    verifyJson(json, new Options());
  }

  public static void verifyJson(String json, Options options)
  {
    verifyJson(json, false, options);
  }

  public static void verifyJson(String json, boolean reorderJson)
  {
    verifyJson(json, reorderJson, new Options());
  }

  public static void verifyJson(String json, boolean reorderJson, Function1<GsonBuilder, GsonBuilder> gsonBuilder)
  {
    verifyJson(json, reorderJson, gsonBuilder, new Options());
  }

  public static void verifyJson(String json, boolean reorderJson, Function1<GsonBuilder, GsonBuilder> gsonBuilder,
      Options options)
  {
    String formattedJson = reorderJson
        ? JsonUtils.reorderFields(json, gsonBuilder)
        : JsonUtils.prettyPrint(json, gsonBuilder);
    Approvals.verify(formattedJson, options.forFile().withExtension(".json"));
  }

  public static void verifyJson(String json, boolean reorderJson, Options options)
  {
    verifyJson(json, reorderJson, g -> g, options);
  }

  public static void verifyAsJson(Object o)
  {
    verifyAsJson(o, new Options());
  }

  public static void verifyAsJson(Object o, Function1<GsonBuilder, GsonBuilder> gsonBuilder)
  {
    verifyAsJson(o, gsonBuilder, new Options());
  }

  public static void verifyAsJson(Object o, Function1<GsonBuilder, GsonBuilder> gsonBuilder, Options options)
  {
    Approvals.verify(JsonUtils.asJson(o, gsonBuilder), options.forFile().withExtension(".json"));
  }

  // begin-snippet: verify_as_json
  public static void verifyAsJson(Object o, Options options)
  {
    Approvals.verify(JsonUtils.asJson(o), options.forFile().withExtension(".json"));
  }
  // end-snippet
}
